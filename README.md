# final project 1단계 - 상품 판매 사이트
___

> ## 기술스택
* Springboot 3.2
* JDK 21
* IntelliJ
* MySQL 8.0

> ## 의존성
* Lombok
* Spring Boot DevTools
* Spring Web
* Spring Data JPA
* MySQL Driver
* Mustache

> ## Product 판매자 기능 구현

### [요구사항]
* PostRepository, PostService 생성
* PostRepository는 EntityManager DI 받아서 만들기
* JPARepository 상속하지 않기


### [핵심 기능 (CRUD)]
* 상품 목록보기
* 상품 등록하기
* 상품 상세보기
* 상품 수정하기
* 상품 삭제하기


> ## 1. MySQL 연동
* username : root / password : 1234
  ![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/01203bfb-0be1-4655-a43f-eeb96d4add88)


* 사용자 생성 및 권한 주기, DB 생성
```
-- 유저 생성 (유저이름@아이피주소)
create user 'root'@'%' identified by '1234';

-- 해당 유저에게 모든 권한 주기 
GRANT ALL PRIVILEGES ON store.* TO 'root'@'%';
CREATE DATABASE store;

use store;
```

> ## 2. application.yml 설정
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/05e4d104-97a8-49a0-b8db-bc643fcb89b4)


> ## 3. build.gradle dekpendencies 설정
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/97f25511-7578-4a58-9afa-95008b32dc62)


> ## 4. Product Entity 생성
```
package com.example.projectstoreproductver1.product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
```


> ## 5. 상품 목록보기


### 5-1. 목록보기 화면
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/2a532c09-e547-498a-9506-696c88c8d10d)


### 5-2. ProductRepository에 findAll()
```
@RequiredArgsConstructor;
@Repository;
public class ProductRepository {
    private final EntityManager em;

    //상품목록보기
    public List<Product> findAll(){
        Query query =
                em.createQuery("select p from Product p order by p.id desc", Product.class);
        return query.getResultList();
    }
}
```


### 5-3. ProductResponse에 MainDTO
* 저장한 데이터를 화면에 뿌리기위해 ProductResponse를 생성
```
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
```


### 5-4. ProductService findAll() 
* 엔티티 받아온걸 dto로 변경
```
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
```

 
### 5-5. ProductController list
```
@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;
    //상품목록보기
    @GetMapping({"/"})
    public String list(HttpServletRequest request){
        List<ProductResponse.MainDTO> productList = productService.findAll();
        request.setAttribute("productList", productList);
        return "product/list";
    }
```


___


> ## 6. 상품 등록하기


### 6-1. 상품 등록하기 화면
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/5a912197-3fe3-4fb2-910e-71eef65cf415)


### 6-1. ProductRepository에 save()
```
public class ProductRequest {
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
```


### 6-2. ProductResponse에 SaveDTO
```
public class ProductResponse {

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

}
```


### 6-3. ProductService에 save()
```
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //상품 등록하기
    @Transactional
    public ProductResponse.SaveDTO save(ProductRequest.SaveDTO reqDTO){
        Product product = productRepo.save(reqDTO.toEntity());
        return new ProductResponse.SaveDTO(product);
    }
}
```


### 6-4. ProductController
```
@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    //상품 등록하기
    @PostMapping("/product/save")
    public String save(ProductRequest.SaveDTO reqDTO){
        productService.save(reqDTO);
        return "redirect:/";
    }

    @GetMapping("/product/save-form")
    public String saveForm(){
        return "product/save-form";
    }
}
```


___


> ## 7. 상품 상세보기


### 7-1. 상품 상세보기 화면
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/b990bf1e-2825-40c7-90cc-dbf14094ca09)

### 7-1. ProductRepository에 findById() 
```
@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final EntityManager em;

    //상품 상세보기
    public Product findById(int id) {
        Product product = em.find(Product.class, id);
        return product;
    }
```

    
### 7-2. ProductRequest에 DetailDTO
```
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
 ```

   
### 7-3. ProductService에 findById()
```
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //상품 상세보기
    public ProductResponse.DetailDTO findById(int id){
        Product product = productRepo.findById(id);
        return new ProductResponse.DetailDTO(product);
    }
 ```

   
### 7-4. ProductController 
```
@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    //상품 상세보기
    @GetMapping("/product/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request){
        ProductResponse.DetailDTO product = productService.findById(id);
        request.setAttribute("product", product);
        return "product/detail";
    }
}
```


___


> ## 8. 상품 수정하기


### 8-1. 상품 수정하기 화면
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/e5fddc81-3ca9-4bf1-ad56-d2575337d52d)
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/d7283d9f-35d0-4fff-b3e2-05552e59fac0)


### 8-2. ProductRequest에 UpdateDTO
```
public class ProductRequest {
    //상품 수정
    @Data
    public static class UpdateDTO{
        private String name;
        private Integer price;
        private Integer qty;
        private MultipartFile img;
    }
}
```


### 8-3. ProductResponse에 UpdateDTO
```
public class ProductResponse {
    //상품 수정
    @Data
    public static class UpdateDTO {
        private int id;
        private String name;
        private int price;
        private int qty;
        private String img;

        public UpdateDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.img = product.getImg();
        }
    }
}
```


### 8-4. ProductRepository에 updateById()
```
@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final EntityManager em;

    //상품 수정하기
    public Product updateById(int id, ProductRequest.UpdateDTO reqDTO) {
        Product product = em.find(Product.class, id);
        product.setName(reqDTO.getName());
        product.setPrice(reqDTO.getPrice());
        product.setQty(reqDTO.getQty());
        product.setImg(ImgSaveUtil.save(reqDTO.getImg()));
        return product;
    }
```


### 8-5. ProductService에 updateById() 
```
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //상품 수정하기
    @Transactional
    public ProductResponse.UpdateDTO updateById(int id, ProductRequest.UpdateDTO reqDTO){
        Product product = productRepo.updateById(id, reqDTO);
        return new ProductResponse.UpdateDTO(product);
    }
}
```


### 8-6. ProductController 
```
@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    // 상품 수정하기
    @PostMapping("/product/{id}/update")
    public String update(@PathVariable int id, ProductRequest.UpdateDTO reqDTO){
        productService.updateById(id, reqDTO); // 이미지 업데이트를 수행하는 서비스 메서드 호출
        return "redirect:/product/" + id;
    }

    @GetMapping("/product/{id}/update-form")
    public String updateForm(@PathVariable int id, HttpServletRequest request){
        ProductResponse.DetailDTO product = productService.findById(id);
        request.setAttribute("product", product);
        return "product/update-form";
    }
}
```

___


> ## 9. 상품 삭제하기


### 9-1. 상품 삭제하기 화면
![image](https://github.com/yuzusim/project-store-product-ver1/assets/153582415/4307696e-da9a-4d7a-a27a-260c4a3f2ae7)


### 9-2. ProductRepository deleteById()
```
@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final EntityManager em;

    //상품 삭제하기
    public void deleteById(int id) {
        Query query =
                em.createQuery("delete from Product p where p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
```


### 9-3. ProductService에 deleteById()
```
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    //상품 삭제하기
    @Transactional
    public void deleteById(int id){
        productRepo.deleteById(id);
    }
```

  
### 9-4. ProductController
```
@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    // 상품 삭제하기
    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable int id){
        productService.deleteById(id);
        return "redirect:/";
    }
}
```
