package com.example.bookstore.dto;

import com.example.bookstore.model.Address;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String phoneNumber,
        String gender,
        String email,
        List<Address> address,
        byte[] avatarImage
) {
}
