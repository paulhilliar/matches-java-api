package com.matchesfashion.papi

import com.matchesfashion.papi.domain.Product

import static java.lang.Math.abs

class DomainTestData {

    static random = new Random()

    static Product product(String title='valid-title') {
        return new Product(id: abs(random.nextInt()),
                title: title,
                category: 'valid-category',
                price: abs(random.nextInt())
        )
    }

}
