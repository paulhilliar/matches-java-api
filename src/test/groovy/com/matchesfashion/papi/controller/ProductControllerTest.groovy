package com.matchesfashion.papi.controller


import com.matchesfashion.papi.domain.Product
import com.matchesfashion.papi.repository.ProductRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static com.matchesfashion.papi.DomainTestData.product
import static com.matchesfashion.papi.TestUtils.marshal
import static com.matchesfashion.papi.TestUtils.unmarshal
import static org.hamcrest.Matchers.equalTo
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.springframework.data.domain.Sort.Direction.ASC
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner)
@WebMvcTest([ProductController])
class ProductControllerTest {

    @Autowired MockMvc mockMvc
    @MockBean ProductRepository productRepository
    @Captor ArgumentCaptor<Pageable> pageableCaptor

    @Test
    void 'test happy path find all'() {
        def product = product('foo')
        when(productRepository.findAll(any(Pageable)))
                .thenReturn(new PageImpl([product]))

        def content = mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.content.length()', equalTo(1)))
                .andExpect(jsonPath('$.content[0].title', equalTo('foo')))
                .andReturn().response.contentAsString
        def page = unmarshal(content, Map)
        def productJson = marshal(page.content[0])
        def returnedProduct = unmarshal(productJson, Product)

        //check that what was returned from the API looked exactly like what we expected from the returned domain object
        assert product == returnedProduct
    }


    @Test
    void 'test happy path find price greater than'() {
        when(productRepository.findByPriceGreaterThan(any(Pageable), eq(250)))
                .thenReturn(new PageImpl([product('foo')]))

        mockMvc.perform(get("/products?priceGreaterThan=250"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.content.length()', equalTo(1)))
    }


    @Test
    void 'test default pagination and sorting'() {
        when(productRepository.findAll(any(Pageable))).thenReturn(new PageImpl([]))

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())

        verify(productRepository).findAll(pageableCaptor.capture());
        def pageable = pageableCaptor.getValue()

        //default sort options should be provided by the controller annotations
        assert pageable.pageNumber == 0
        assert pageable.pageSize == 25
        assert pageable.sort == Sort.by(ASC, "title")
    }


    @Test
    void 'test pagination and sorting provided by query params'() {
        when(productRepository.findAll(any(Pageable))).thenReturn(new PageImpl([]))

        mockMvc.perform(get("/products?page=2&pageSize=15&sort=price"))
                .andExpect(status().isOk())

        verify(productRepository).findAll(pageableCaptor.capture());
        def pageable = pageableCaptor.getValue()

        assert pageable.pageNumber == 2
        assert pageable.pageSize == 15
        assert pageable.sort == Sort.by(ASC, "price")
    }



    static class ProductPage extends PageImpl<Product> {
        ProductPage() {
            super([])
        }
    }

}
