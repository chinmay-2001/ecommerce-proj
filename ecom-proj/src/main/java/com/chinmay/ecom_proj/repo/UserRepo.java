package com.chinmay.ecom_proj.repo;

import com.chinmay.ecom_proj.model.Product;
import com.chinmay.ecom_proj.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<Users   ,Integer> {
    @Query("SELECT u FROM Users u WHERE u.email = :email AND u.password = :password")
    Users findByEmailandPassword(String email, String password);
}
