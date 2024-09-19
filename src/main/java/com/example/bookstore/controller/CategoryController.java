package com.example.bookstore.controller;

import com.example.bookstore.model.Category;
import com.example.bookstore.service.serviceinterface.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory(){
        return categoryService.selectAllCategory();
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category){
        return categoryService.insertCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category, @PathVariable String id){
        return categoryService.updateCategory(category, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        return categoryService.deleteCategory(id);
    }
}
