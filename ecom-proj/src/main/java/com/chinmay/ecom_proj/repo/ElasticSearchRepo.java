package com.chinmay.ecom_proj.repo;

import com.chinmay.ecom_proj.model.searchProductModal;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;

import java.util.List;

public interface ElasticSearchRepo extends ElasticsearchRepository<searchProductModal,Integer> {
    @Query("{ \"multi_match\": { \"query\": \"?0\", \"fields\": [\"name\", \"description\"] } }")
    List<searchProductModal> searchByNameAndDescription(String query);
}
