package com.example.bookstore.dto;

import java.util.List;
import java.util.UUID;

public record BookDto(
   UUID id,
   String title,
   String author,
   String description,
   Integer price,
   Integer inventory,
   List<String> categories,
   byte[] image
) {
}
