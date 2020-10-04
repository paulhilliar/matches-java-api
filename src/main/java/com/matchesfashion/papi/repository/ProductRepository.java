package com.matchesfashion.papi.repository;

import com.matchesfashion.papi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    Page findByPriceGreaterThan(Pageable pageable, int priceGreaterThan);

}
