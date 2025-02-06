package com.chinmay.ecom_proj.service;

import com.chinmay.ecom_proj.ProductSpecification;
import com.chinmay.ecom_proj.model.FilterDataResponse;
import com.chinmay.ecom_proj.model.Product;
import com.chinmay.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    @Async
    public List<Product> getAllProduct(Map<String,String> filter){

        Specification<Product> spec=ProductSpecification.filterBy(filter);
        System.out.println(spec);

        List<Product> products=repo.findAll(spec);

        return products;
    }

    @Async
    public Product getProductById(int id) {

        return repo.findById(id).orElse(null);
    }

    @Async
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        System.out.println(product);
        System.out.println(imageFile);
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    @Async
    public byte[] getDetaultImage() {
        try{
            InputStream inputStream=getClass().getResourceAsStream("/static/images/DefaultImage.png");
            return  inputStream.readAllBytes();
        }catch (IOException e){
            return new byte[0];
        }
    }

    @Async
    public Product updateProduct( Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    @Async
    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    @Async
    public List<Product> searchProduct(String keyword) {
        return repo.searchProducts(keyword);
    }

    @Async
    public FilterDataResponse getFilterData() {
        FilterDataResponse res=new FilterDataResponse();

        List<String> categories=repo.findDistinctCategories();
        List<String> brands=repo.findDistinctBrand();

        res.setBrands(brands);
        res.setCategories(categories);
        return res;
    }
}
