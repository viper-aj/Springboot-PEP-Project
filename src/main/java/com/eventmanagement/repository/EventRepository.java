package com.eventmanagement.repository;

import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerId(Long id);
    List<Event> findByStatusAndCategoryId(EventStatus status, Long categoryId);
    List<Event> findByStatus(EventStatus status);
    List<Event> findByCategoryId(Long categoryId);
}
