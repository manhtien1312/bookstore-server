package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getAddress(),
                user.getAvatarImage()
        );
    }
}
