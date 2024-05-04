package com.example.projectstoreproductver1.product;

import lombok.Data;

public class ProductResponse {
    @Data
    public static class MainDTO{
        private int id;
        private String name;
        private int price;
        private int qty;

        public MainDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
        }
    }
}
