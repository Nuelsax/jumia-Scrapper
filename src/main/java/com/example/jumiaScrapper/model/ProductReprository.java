package com.example.jumiaScrapper.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReprository extends JpaRepository<Product, Long> {

    Product findByDataId(String dataId);
}
