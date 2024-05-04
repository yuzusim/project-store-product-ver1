package com.example.projectstoreproductver1.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    //상품목록보기
    @GetMapping({"/"})
    public String list(){
        return "product/list";
    }

    //상품 상세보기
    @GetMapping("/product/{id}")
    public String detail(@PathVariable int id){
        return "product/detail";
    }

    //상품 등록하기
    @PostMapping("/product/save")
    public String save(){
        return "redirect:/";
    }

    @GetMapping("/product/save-form")
    public String saveForm(){
        return "product/save-form";
    }

    // 상품 수정하기
    @PostMapping("/product/{id}/update")
    public String update(@PathVariable int id){
        return "redirect:/product{id}";
    }

    @GetMapping("/product/{id}/update-form")
    public String updateForm(@PathVariable int id){
        return "product/update-form";
    }

    // 상품 삭제하기
    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable int id){
        return "redirect:/";
    }
}
