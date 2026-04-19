package org.example.audit.domain.db;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, UUID> {

    @Modifying
    @Query(value = """
        INSERT INTO audit.processed_events (event_id, processed_at)
        VALUES (:eventId, now())
        ON CONFLICT DO NOTHING 
        """,
        nativeQuery = true)
    int insertIfNotExists(@Param("eventId") UUID eventId);
}
