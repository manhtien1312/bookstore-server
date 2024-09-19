package com.example.bookstore.repository;

import com.example.bookstore.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @EntityGraph(attributePaths = "roles")
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Account acc SET acc.password=:newPassword WHERE acc.user.id=:userId")
    void updatePasswordByUserId(String newPassword, int userId);

}
