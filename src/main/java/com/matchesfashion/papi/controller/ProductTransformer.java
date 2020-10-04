package com.matchesfashion.papi.controller;

import com.matchesfashion.papi.api.Product;

public class ProductTransformer {

    public static Product toApi(com.matchesfashion.papi.domain.Product domain) {
        return Product.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .category(domain.getCategory())
                .price(domain.getPrice())
                .build();
    }

}
