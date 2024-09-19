package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getAvatarImage(),
                user.getAddress().toString()
        );
    }
}
