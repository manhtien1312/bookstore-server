package com.example.bookstore.payload.request;

import java.util.Set;

public record SignUpRequest(
        String name,
        String gender,
        String phoneNumber,
        String email,
        String password,
        Set<String> roles
) {
}