package com.example.bookstore.controller;

import com.example.bookstore.model.Cart;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.service.serviceinterface.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllItems(){
        return cartService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addItem(@RequestBody Cart cart){
        return cartService.addItemCart(cart);
    }

}
