package com.example.bookstore.dto;

public record UserDTO(
        Integer id,
        String name,
        String phoneNumber,
        String gender,
        byte[] avatarImage,
        String address
) {
}
