package com.example.bookstore.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String phoneNumber,
        String gender,
        byte[] avatarImage,
        String address
) {
}
