package com.chinmay.ecom_proj.controller;

import com.chinmay.ecom_proj.model.CartItem;
import com.chinmay.ecom_proj.model.CartItemDTO;
import com.chinmay.ecom_proj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CartController {
        @Autowired
        private CartService cartService;

        @GetMapping("/cart/{userId}")
        public ResponseEntity<?> getCartList(@PathVariable int userId) {
            try {
                return new ResponseEntity<>(cartService.getCartList(userId), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/cart")
        public  ResponseEntity<?> postCart(@RequestBody CartItemDTO cartItemDTO){
            try {
                cartService.createCartItem(cartItemDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<?> deletCart(@PathVariable int cartItemId){
            try{
                cartService.deleteCartItem(cartItemId);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
