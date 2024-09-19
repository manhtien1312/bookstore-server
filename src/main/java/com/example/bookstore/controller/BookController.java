package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.payload.response.MessageResponse;
import com.example.bookstore.service.serviceinterface.IBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllBooks (){
        return bookService.getAllBook();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addBook(@RequestParam String bookDtoStr, @RequestParam MultipartFile image) {
        return bookService.insertBook(bookDtoStr, image);
    }

    @PostMapping("/add-excel-file")
    public ResponseEntity<MessageResponse> addThroughFile(@RequestParam MultipartFile booksFile) throws IOException {
        return bookService.insertThroughFile(booksFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBook (@PathVariable String id){
        return bookService.deleteBook(id);
    }

}
