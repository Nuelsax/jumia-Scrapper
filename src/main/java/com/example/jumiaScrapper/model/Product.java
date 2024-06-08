package com.example.jumiaScrapper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_db")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long price;
    @Column(name = "sku", unique = true)
    private  String dataId;
    private boolean inStock;

    public Product(String name, long price, String dataId) {
        this.name = name;
        this.price = price;
        this.dataId = dataId;
    }

    public Product(String name, long price, String dataId, boolean inStock) {
        this.name = name;
        this.price = price;
        this.dataId = dataId;
        this.inStock = inStock;
    }

}

