package com.chinmay.ecom_proj.service;

import com.chinmay.ecom_proj.ProductSpecification;
import com.chinmay.ecom_proj.model.FilterDataResponse;
import com.chinmay.ecom_proj.model.Product;
import com.chinmay.ecom_proj.model.searchProductModal;
import com.chinmay.ecom_proj.repo.ElasticSearchRepo;
import com.chinmay.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    @Autowired
    ElasticSearchRepo elasticSearchRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

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
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        repo.save(product);
        searchProductModal searchProductModal=new searchProductModal();
        searchProductModal.setId(product.getId());
        searchProductModal.setName(product.getName());
        searchProductModal.setDescription(product.getDescription());
        searchProductModal.setPrice(product.getPrice());
        elasticSearchRepo.save(searchProductModal);

        return product;
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
    public List<searchProductModal> searchProduct(String keyword) {
//        Criteria criteria = new Criteria("name").contains(keyword)
//                .or(new Criteria("description").contains(keyword));

        //fuzzy search
        Criteria criteria = new Criteria("name").fuzzy(keyword)
                .or(new Criteria("description").fuzzy(keyword));

        CriteriaQuery query = new CriteriaQuery(criteria);

        SearchHits<searchProductModal> searchHits = elasticsearchOperations.search(query, searchProductModal.class);

//        return elasticSearchRepo.searchByNameAndDescription(keyword);
        return searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
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
