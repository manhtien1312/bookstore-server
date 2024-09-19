package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.model.User;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    UserDTO findByAccountEmail(String accountEmail);

    UserDTO getUserInfor();

    void saveUser(User user);

    ResponseEntity<MessageResponse> updateUser(UserDTO userDTO);

    ResponseEntity<MessageResponse> updateAvatarImage(MultipartFile avatarImage);

}
