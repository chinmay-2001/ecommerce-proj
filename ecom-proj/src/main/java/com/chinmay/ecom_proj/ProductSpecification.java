package com.chinmay.ecom_proj;

import com.chinmay.ecom_proj.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {
    public static Specification<Product> filterBy(Map<String,String> filter){

        return (root,query,cb)->{

            List<Predicate> predicates=new ArrayList<>();

            filter.forEach((key,value)->{
                if(value!=null && !value.isEmpty()) {
                    predicates.add(cb.equal(root.get(key),value));
                }
            });
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
