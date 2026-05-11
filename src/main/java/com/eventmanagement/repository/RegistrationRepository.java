package com.eventmanagement.repository;

import com.eventmanagement.entity.Registration;
import com.eventmanagement.entity.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByUserId(Long userId);
    Optional<Registration> findByUserIdAndEventId(Long userId, Long eventId);
    long countByEventIdAndStatus(Long eventId, RegistrationStatus status);
    Optional<Registration> findByIdAndUserId(Long id, Long userId);
}
