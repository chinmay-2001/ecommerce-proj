package com.chinmay.ecom_proj.model;

import java.util.List;

public class CartResponseDTO {
    private int cartId;
    private List<CartItemProjection> cartItems;

    public CartResponseDTO(int cartId, List<CartItemProjection> cartItems) {
        this.cartId = cartId;
        this.cartItems = cartItems;
    }

    public int getCartId() {
        return cartId;
    }

    public List<CartItemProjection> getCartItems() {
        return cartItems;
    }

}
