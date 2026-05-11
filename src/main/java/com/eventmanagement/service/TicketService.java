package com.eventmanagement.service;

import com.eventmanagement.dto.request.TicketRequestDTO;
import com.eventmanagement.dto.response.TicketResponseDTO;

public interface TicketService {
    TicketResponseDTO createTicket(TicketRequestDTO requestDTO);
    TicketResponseDTO getTicketById(Long id);
    TicketResponseDTO getTicketByRegistrationId(Long registrationId);
    void deleteTicket(Long id);
}
