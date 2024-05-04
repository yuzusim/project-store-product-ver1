package com.example.projectstoreproductver1.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //상품 상세보기
    public ProductResponse.DetailDTO findById(int id){
        Product product = productRepo.findById(id);
        return new ProductResponse.DetailDTO(product);
    }

    //상품 등록하기
    @Transactional
    public ProductResponse.SaveDTO save(ProductRequest.SaveDTO reqDTO){
        Product product = productRepo.save(reqDTO.toEntity());
        return new ProductResponse.SaveDTO(product);
    }

    //상품 목록보기
    public List<ProductResponse.MainDTO> findAll(){
        List<Product> productList = productRepo.findAll();
        return productList.stream().map(ProductResponse.MainDTO::new).toList();
    }
}