package com.example.bookstore.repository;

import com.example.bookstore.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Transactional
    @Query(value = "SELECT c FROM Cart c WHERE c.user.id=:userId")
    List<Cart> findByUserId(UUID userId);

}
