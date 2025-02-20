package com.chinmay.ecom_proj.repo;

import com.chinmay.ecom_proj.model.CartItem;
import com.chinmay.ecom_proj.model.CartItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    @Query("SELECT ci.id as id,ci.price as price, ci.quantity as quantity," +
            "p.id as productId, p.name as name,p.description as description,p.brand as brand,p.category  as category," +
            "p.imageName as imageName,p.imageType as imageType,p.imageData as imageData " +
            "FROM CartItem ci JOIN ci.cart c JOIN ci.product p WHERE c.users.id= :userId")
    List<CartItemProjection> getCartItemByUserId(int userId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id=:userId AND ci.product.id=:productId")
    CartItem getCartItemByUserIdAndProductId(int userId,int productId);
}
