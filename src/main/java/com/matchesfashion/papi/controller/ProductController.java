package com.matchesfashion.papi.controller;

import com.matchesfashion.papi.api.Product;
import com.matchesfashion.papi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    /**
     * Query for products
     *
     * e.g.
     * curl 'localhost:8080/products?size=2&page=1&priceGreaterThan=5&sort=title' | jq
     *
     * Query params:
     * @param page (optional - default 0) - zero-based page number
     * @param pageSize (optional - default 25) - size of the page to be returned
     * @param sort (optional - default 'title')
     * @param priceGreaterThan (optional) -
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity listProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int pageSize,
            @RequestParam(required = false, defaultValue = "title") String sort,
            @RequestParam(required = false) Integer priceGreaterThan) {

        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sort));

            Page<com.matchesfashion.papi.domain.Product> result;
            if (priceGreaterThan != null) {
                result = productRepository.findByPriceGreaterThan(pageable, priceGreaterThan);
            } else {
                result = productRepository.findAll(pageable);
            }
            return ResponseEntity.ok(transformToApi(result));
        }
        catch (PropertyReferenceException e) {
            //the sort criteria field was rejected by spring data
            return ResponseEntity.badRequest().body("Sort field was not found");
        }
    }

    private Page<Product> transformToApi(Page<com.matchesfashion.papi.domain.Product> page) {
        return new PageImpl(
                page.getContent().stream().map(ProductTransformer::toApi).collect(toList()),
                page.getPageable(), page.getTotalElements());
    }

}
