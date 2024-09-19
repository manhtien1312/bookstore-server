package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.model.Account;
import com.example.bookstore.payload.request.SignInRequest;
import com.example.bookstore.payload.request.SignUpRequest;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    ResponseEntity<MessageResponse> changePassword(String password);

    ResponseEntity<?> register(SignUpRequest request) throws Exception;

    ResponseEntity<?> signIn(SignInRequest request);

    MessageResponse checkAccount(String email);

}
