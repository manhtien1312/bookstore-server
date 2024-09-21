package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Transactional
    @Query(value = "SELECT b FROM Book b " +
            "JOIN b.categories c " +
            "WHERE c.categoryName=:category")
    List<Book> findByCategory(String category);

    @Transactional
    @Query(value = "SELECT b FROM Book b " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT(:searchText, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "ORDER BY b.title")
    List<Book> search(String searchText);
}
