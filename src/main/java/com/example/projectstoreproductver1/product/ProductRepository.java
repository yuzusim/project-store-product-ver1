package com.example.projectstoreproductver1.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final EntityManager em;

    //상품 등록하기
    @Transactional
    public Product save(Product product) {
        em.persist(product);
        return product;
    }

    //상품목록보기
    public List<Product> findAll(){
        Query query =
                em.createQuery("select p from Product p order by p.id desc", Product.class);
        return query.getResultList();

    }

}
