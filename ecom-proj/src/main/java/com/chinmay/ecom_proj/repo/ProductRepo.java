package com.chinmay.ecom_proj.repo;

import com.chinmay.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {
    //JPQL
    @Query("SELECT p from  Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
            "LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
            "LOWER(p.brand) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
            "LOWER(p.category) LIKE LOWER(CONCAT('%',:keyword,'%'))"
    )
    List<Product> searchProducts(String keyword);

    @Query("SELECT DISTINCT p.category from Product p")
    List<String> findDistinctCategories();

    @Query("SELECT DISTINCT p.brand from Product p")
    List<String> findDistinctBrand();
}
