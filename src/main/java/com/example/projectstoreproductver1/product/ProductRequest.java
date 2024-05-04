package com.example.projectstoreproductver1.product;

import com.example.projectstoreproductver1._core.common.ImgSaveUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {
    //상품 수정
    @Data
    public static class UpdateDTO{
        private String name;
        private Integer price;
        private Integer qty;
        private MultipartFile img;
    }

    //상품 등록
    @Data
    public static class SaveDTO{
        private String name;
        private Integer price;
        private Integer qty;
        private MultipartFile img;

        public Product toEntity() {
            String imgFileName = ImgSaveUtil.save(img);
            return Product.builder()
                    .img(imgFileName)
                    .name(name)
                    .price(price)
                    .qty(qty)
                    .build();
        }
    }
}
