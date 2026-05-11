package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.RegistrationRequestDTO;
import com.eventmanagement.dto.response.RegistrationResponseDTO;
import com.eventmanagement.entity.*;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.RegistrationRepository;
import com.eventmanagement.repository.TicketRepository;
import com.eventmanagement.repository.UserRepository;
import com.eventmanagement.service.RegistrationService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final DTOMapper mapper;

    @Override
    @Transactional
    public RegistrationResponseDTO registerForEvent(RegistrationRequestDTO requestDTO) {
        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + requestDTO.getEventId()));
        
        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new IllegalStateException("Event is not published");
        }

        long confirmedRegistrations = registrationRepository.countByEventIdAndStatus(event.getId(), RegistrationStatus.CONFIRMED);
        if (confirmedRegistrations >= event.getCapacity()) {
            throw new IllegalStateException("Event is full");
        }

        if (registrationRepository.findByUserIdAndEventId(requestDTO.getUserId(), requestDTO.getEventId()).isPresent()) {
            throw new IllegalStateException("User is already registered for this event");
        }

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDTO.getUserId()));

        Registration registration = Registration.builder()
                .user(user)
                .event(event)
                .status(RegistrationStatus.CONFIRMED)
                .build();
        
        registration = registrationRepository.save(registration);

        Ticket ticket = Ticket.builder()
                .registration(registration)
                .ticketType("GENERAL")
                .price(0.0)
                .build();
        ticketRepository.save(ticket);

        return mapper.toRegistrationResponseDTO(registration);
    }

    @Override
    public List<RegistrationResponseDTO> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId).stream()
                .map(mapper::toRegistrationResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistrationResponseDTO> getRegistrationsByUser(Long userId) {
        return registrationRepository.findByUserId(userId).stream()
                .map(mapper::toRegistrationResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationResponseDTO cancelRegistration(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + id));
        registration.setStatus(RegistrationStatus.CANCELLED);
        return mapper.toRegistrationResponseDTO(registrationRepository.save(registration));
    }

    @Override
    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registration not found with id: " + id);
        }
        registrationRepository.deleteById(id);
    }
}
