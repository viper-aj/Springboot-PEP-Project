package com.eventmanagement.repository;

import com.eventmanagement.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByEventId(Long eventId);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.event.id = :eventId")
    Double findAverageRatingByEventId(@Param("eventId") Long eventId);
}
