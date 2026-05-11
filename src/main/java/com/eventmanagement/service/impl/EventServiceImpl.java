package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.EventRequestDTO;
import com.eventmanagement.dto.response.EventResponseDTO;
import com.eventmanagement.entity.Category;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.EventStatus;
import com.eventmanagement.entity.User;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.CategoryRepository;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.UserRepository;
import com.eventmanagement.repository.VenueRepository;
import com.eventmanagement.service.EventService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final CategoryRepository categoryRepository;
    private final DTOMapper mapper;

    @Override
    public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
        User organizer = userRepository.findById(requestDTO.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + requestDTO.getOrganizerId()));
        Venue venue = venueRepository.findById(requestDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + requestDTO.getVenueId()));
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        Event event = Event.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .date(requestDTO.getDate())
                .status(EventStatus.DRAFT)
                .capacity(requestDTO.getCapacity())
                .organizer(organizer)
                .venue(venue)
                .category(category)
                .build();

        return mapper.toEventResponseDTO(eventRepository.save(event));
    }

    @Override
    public Page<EventResponseDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(mapper::toEventResponseDTO);
    }

    @Override
    public EventResponseDTO getEventById(Long id) {
        return mapper.toEventResponseDTO(eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id)));
    }

    @Override
    public List<EventResponseDTO> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId).stream()
                .map(mapper::toEventResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDTO> filterEvents(EventStatus status, Long categoryId) {
        List<Event> events;
        if (status != null && categoryId != null) {
            events = eventRepository.findByStatusAndCategoryId(status, categoryId);
        } else if (status != null) {
            events = eventRepository.findByStatus(status);
        } else if (categoryId != null) {
            events = eventRepository.findByCategoryId(categoryId);
        } else {
            events = eventRepository.findAll();
        }
        return events.stream().map(mapper::toEventResponseDTO).collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        User organizer = userRepository.findById(requestDTO.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + requestDTO.getOrganizerId()));
        Venue venue = venueRepository.findById(requestDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + requestDTO.getVenueId()));
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        event.setTitle(requestDTO.getTitle());
        event.setDescription(requestDTO.getDescription());
        event.setDate(requestDTO.getDate());
        event.setCapacity(requestDTO.getCapacity());
        event.setOrganizer(organizer);
        event.setVenue(venue);
        event.setCategory(category);

        return mapper.toEventResponseDTO(eventRepository.save(event));
    }

    @Override
    public EventResponseDTO publishEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        if (event.getVenue() == null) {
            throw new IllegalStateException("Cannot publish event without a venue");
        }
        event.setStatus(EventStatus.PUBLISHED);
        return mapper.toEventResponseDTO(eventRepository.save(event));
    }

    @Override
    public EventResponseDTO cancelEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        event.setStatus(EventStatus.CANCELLED);
        return mapper.toEventResponseDTO(eventRepository.save(event));
    }

    @Override
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}
