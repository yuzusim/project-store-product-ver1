package com.example.projectstoreproductver1.product;

import lombok.Data;

public class ProductResponse {

    //상품 상세보기
    @Data
    public static class DetailDTO {
        private int id;
        private String name;
        private int price;
        private int qty;
        private String img;

        public DetailDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.img = product.getImg();
        }
    }


    //상품 등록
    @Data
    public static class SaveDTO{
        private int id;
        private String name;
        private int price;
        private int qty;
        private String img;

        public SaveDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.img = product.getImg();
        }
    }

    //상품 목록
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
