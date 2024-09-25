package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.User;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByAccountEmail(String accountEmail);

    UserDto getUserInfor();

    void saveUser(User user);

    ResponseEntity<MessageResponse> updateUser(UserDto userDTO);

    ResponseEntity<MessageResponse> updateAvatarImage(MultipartFile avatarImage);

}
