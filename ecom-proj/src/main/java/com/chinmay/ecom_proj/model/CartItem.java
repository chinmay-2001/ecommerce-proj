package com.chinmay.ecom_proj.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart; // Reference to the Cart

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Reference to the Product

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price; // Store price at the time of adding

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}