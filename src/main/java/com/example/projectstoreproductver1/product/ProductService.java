package com.example.projectstoreproductver1.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //상품 목록보기
    public List<ProductResponse.MainDTO> findAll(){
        List<Product> productList = productRepo.findAll();
        return productList.stream().map(ProductResponse.MainDTO::new).toList();
    }
}