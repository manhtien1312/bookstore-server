package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface IBookService {

    ResponseEntity<?> getAllBook();
    ResponseEntity<?> filterBooks(String category);
    ResponseEntity<?> searchBooks(String searchText);
    ResponseEntity<MessageResponse> insertBook(String bookDtoStr, MultipartFile image);
    ResponseEntity<MessageResponse> insertThroughFile(MultipartFile booksFile) throws IOException;
    ResponseEntity<MessageResponse> deleteBook(String id);

}
