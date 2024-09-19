package com.example.bookstore.payload.request;
public record SignInRequest(
    String email,
    String password
) { }
