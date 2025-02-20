package com.chinmay.ecom_proj.repo;

import com.chinmay.ecom_proj.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartRepo extends JpaRepository<Cart,Integer> {

    @Query("SELECT c.id from Cart c WHERE c.users.id=:userId")
    int getIdByUserId(int userId);
}
