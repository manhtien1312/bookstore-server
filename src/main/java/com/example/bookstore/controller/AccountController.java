package com.example.bookstore.controller;

import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.service.serviceinterface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PutMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(@RequestParam String newPassword){
        return accountService.changePassword(newPassword);
    }

}
