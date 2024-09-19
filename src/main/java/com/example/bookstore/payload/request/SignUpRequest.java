package com.example.bookstore.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignUpRequest(

        @NotBlank(message = "Tên không được để trống!")
        @NotNull(message = "Tên không được để trống!")
        String name,
        String gender,
        String phoneNumber,

        @Email(message = "Email không đúng định dạng!")
        @NotBlank(message = "Email không được để trống!")
        @NotNull(message = "Email không được để trống!")
        String email,

        @NotNull(message = "Mật khẩu không được để trống!")
        @Size(min = 6, message = "Độ dài mật khẩu ít nhất 6 ký tự!")
        String password,
        Set<String> roles
) {
}