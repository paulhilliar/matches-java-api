package com.matchesfashion.papi

import com.matchesfashion.papi.repository.ProductRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc

import static org.hamcrest.Matchers.equalTo
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringJUnit4ClassRunner)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [PapiApplication])
class EndToEndTest {

    @Autowired MockMvc mockMvc
    @Autowired ProductRepository productRepository

    @Test
    void 'test basic end to end happy path'() {
        //this is just an integration test to check that everything hangs together OK
        //More detailed testing is elsewhere
        int numOfRecords = productRepository.count()

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.content.length()', equalTo(numOfRecords)))
    }

    @Test
    void 'test sort criteria rejected by Spring data'() {
        mockMvc.perform(get("/products?sort=foo"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Sort field was not found"))
    }

}
