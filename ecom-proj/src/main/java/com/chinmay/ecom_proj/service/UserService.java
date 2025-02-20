package com.chinmay.ecom_proj.service;

import com.chinmay.ecom_proj.model.Cart;
import com.chinmay.ecom_proj.model.CartStatus;
import com.chinmay.ecom_proj.model.Users;
import com.chinmay.ecom_proj.repo.CartRepo;
import com.chinmay.ecom_proj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo repo;

    @Autowired
    CartRepo cartRepo;
    public Users createUser(Users user) {
        Users users=repo.save(user);
        if( users!=null){
            Cart cart=new Cart();
            cart.setUsers(users);
            cart.setStatus(CartStatus.ACTIVE);
            cartRepo.save(cart);
        }else{
             throw new RuntimeException("Some error while creating users");
        }
        return users;
    }

    public Users verifyUser(String email, String password) {
        return  repo.findByEmailandPassword(email,password);
    }
}
