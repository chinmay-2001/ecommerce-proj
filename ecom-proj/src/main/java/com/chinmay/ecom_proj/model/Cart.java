package com.chinmay.ecom_proj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;

    @Column(nullable = false)
    private CartStatus Status;

    public Cart(int id, Users users, CartStatus status) {
        this.id = id;
        this.users = users;
        Status = status;
    }
    public  Cart(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public CartStatus getStatus() {
        return Status;
    }

    public void setStatus(CartStatus status) {
        Status = status;
    }
}
