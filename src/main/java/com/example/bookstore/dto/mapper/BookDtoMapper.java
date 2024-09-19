package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class BookDtoMapper implements Function<Book, BookDto> {
    @Override
    public BookDto apply(Book book) {
        List<String> categories = new ArrayList<>();
        for (Category category : book.getCategories()){
            categories.add(category.getCategoryName());
        }
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPrice(),
                book.getInventory(),
                categories,
                book.getImage()
        );
    }
}
