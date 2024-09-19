package com.example.bookstore.controller;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.service.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/information")
    public UserDTO getUserInfo() {
        return userService.getUserInfor();
    }

    @PutMapping("/update-information")
    public ResponseEntity<MessageResponse> updateUserInfo(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @PutMapping("/update-avatar")
    public ResponseEntity<MessageResponse> updateAvatar(@RequestParam MultipartFile avatarImage) throws IOException {
        return userService.updateAvatarImage(avatarImage);
    }

}
