package org.example.payment.infrastructure.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @EntityGraph(attributePaths = "payments")
    @Query("select u from UserEntity u")
    List<UserEntity> getAllWithPayments();

    boolean existsByEmailIgnoreCase(String email);
}
