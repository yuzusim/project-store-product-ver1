package com.example.projectstoreproductver1.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "product_tb")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 20, nullable = false)
    private String name; //상품명

    @Column(nullable = false)
    private Integer price; //가격

    @Column(nullable = false)
    private Integer qty; //수량

    //@Column(nullable = false)
    private String img; //상품이미지

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Product(Integer id, String name, Integer price, Integer qty, String img, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.img = img;
        this.createdAt = createdAt;
    }

}
