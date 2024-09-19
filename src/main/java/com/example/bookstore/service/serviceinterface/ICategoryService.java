package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.model.Category;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {

    ResponseEntity<?> selectAllCategory();
    ResponseEntity<MessageResponse> insertCategory(Category category);
    ResponseEntity<MessageResponse> updateCategory(Category category, String id);
    ResponseEntity<MessageResponse> deleteCategory(String id);

}
