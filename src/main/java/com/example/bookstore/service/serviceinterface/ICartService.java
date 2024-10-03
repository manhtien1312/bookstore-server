package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.model.Cart;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICartService {

    ResponseEntity<?> getAllItems();
    ResponseEntity<MessageResponse> addItemCart(Cart cart);

}
