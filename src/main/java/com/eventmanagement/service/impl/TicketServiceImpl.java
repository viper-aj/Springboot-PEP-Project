package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.TicketRequestDTO;
import com.eventmanagement.dto.response.TicketResponseDTO;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.entity.Ticket;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.RegistrationRepository;
import com.eventmanagement.repository.TicketRepository;
import com.eventmanagement.service.TicketService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final RegistrationRepository registrationRepository;
    private final DTOMapper mapper;

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO requestDTO) {
        Registration registration = registrationRepository.findById(requestDTO.getRegistrationId())
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + requestDTO.getRegistrationId()));

        Ticket ticket = Ticket.builder()
                .registration(registration)
                .ticketType(requestDTO.getTicketType())
                .price(requestDTO.getPrice())
                .build();
        
        return mapper.toTicketResponseDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        return mapper.toTicketResponseDTO(ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id)));
    }

    @Override
    public TicketResponseDTO getTicketByRegistrationId(Long registrationId) {
        return mapper.toTicketResponseDTO(ticketRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found for registration id: " + registrationId)));
    }

    @Override
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }
}
