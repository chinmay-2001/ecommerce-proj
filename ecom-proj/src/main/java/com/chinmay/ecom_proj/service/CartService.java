package com.chinmay.ecom_proj.service;


import com.chinmay.ecom_proj.model.*;

import com.chinmay.ecom_proj.repo.CartItemRepo;
import com.chinmay.ecom_proj.repo.CartRepo;
import com.chinmay.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    public CartResponseDTO getCartList(int userId){
        List<CartItemProjection> cartItems=cartItemRepo.getCartItemByUserId(userId);
        int cartId=cartRepo.getIdByUserId(userId);
        return new CartResponseDTO(cartId,cartItems);
    }

    public boolean createCartItem(CartItemDTO cartItemDTO) {
        Cart cart = cartRepo.findById(cartItemDTO.getCartId()).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        Product product = productRepo.findById(cartItemDTO.getProductId()).orElseThrow(() -> new RuntimeException("product  not found"));

        CartItem foundCartItem= cartItemRepo.getCartItemByUserIdAndProductId(cart.getId(), product.getId());
        int quantity = cartItemDTO.getQuantity();
        int price = cartItemDTO.getPrice();


        if(foundCartItem.getId()!=null){
            foundCartItem.setQuantity(cartItemDTO.getQuantity());
            foundCartItem.setPrice(cartItemDTO.getPrice());
            cartItemRepo.save(foundCartItem);
            return true;
        }else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setPrice(price);
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
            return true;
        }
    }

    public void deleteCartItem(int cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }
}
