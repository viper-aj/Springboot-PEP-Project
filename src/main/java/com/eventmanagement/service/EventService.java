package com.eventmanagement.service;

import com.eventmanagement.dto.request.EventRequestDTO;
import com.eventmanagement.dto.response.EventResponseDTO;
import com.eventmanagement.entity.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    EventResponseDTO createEvent(EventRequestDTO requestDTO);
    Page<EventResponseDTO> getAllEvents(Pageable pageable);
    EventResponseDTO getEventById(Long id);
    List<EventResponseDTO> getEventsByOrganizer(Long organizerId);
    List<EventResponseDTO> filterEvents(EventStatus status, Long categoryId);
    EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO);
    EventResponseDTO publishEvent(Long id);
    EventResponseDTO cancelEvent(Long id);
    void deleteEvent(Long id);
}
