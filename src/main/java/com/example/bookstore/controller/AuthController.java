package com.example.bookstore.controller;

import com.example.bookstore.payload.request.SignInRequest;
import com.example.bookstore.payload.request.SignUpRequest;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.service.serviceinterface.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) throws Exception{
        return accountService.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request){
        return accountService.signIn(request);
    }

    @PostMapping("check-exist")
    public MessageResponse checkAccount(@RequestParam String email){
        return accountService.checkAccount(email);
    }

}
