package com.chinmay.ecom_proj.controller;

import com.chinmay.ecom_proj.model.FilterDataResponse;
import com.chinmay.ecom_proj.model.Product;
import com.chinmay.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam Map<String,String> params){
        List<Product> products=service.getAllProduct(params);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product=service.getProductById(id);
        if(product!=null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public  ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1=service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product=service.getProductById(productId);
        byte[] imageFile=product.getImageData();
        if(imageFile==null){
            imageFile= service.getDetaultImage();
        }

        String mimeType=product.getImageType()!=null ?product.getImageType():"image/png";
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(mimeType))
                .body(imageFile);
    }

    @PutMapping("/product")
    public  ResponseEntity<String> updateProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){

        Product updateProduct=null;
        try {
            updateProduct = service.updateProduct(product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
        }

        if(updateProduct!=null){
            return new ResponseEntity<>("updated",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=service.getProductById(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filters")
    public  ResponseEntity<FilterDataResponse> getFilterData(){
        FilterDataResponse res=service.getFilterData();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/product/search")
    public  ResponseEntity<List<Product>>  searchProducts(@RequestParam String  keyword){
        List<Product> products=service.searchProduct(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
