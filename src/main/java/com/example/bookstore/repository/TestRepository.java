package com.example.bookstore.repository;

import com.example.bookstore.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestRepository extends JpaRepository<TestModel, UUID> {
}
