package com.example.bookstore.payload.response;

import java.util.List;
public record JwtResponse(
        String token,
        List<String> roles
) {

}
