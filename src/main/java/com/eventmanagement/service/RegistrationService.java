package com.eventmanagement.service;

import com.eventmanagement.dto.request.RegistrationRequestDTO;
import com.eventmanagement.dto.response.RegistrationResponseDTO;

import java.util.List;

public interface RegistrationService {
    RegistrationResponseDTO registerForEvent(RegistrationRequestDTO requestDTO);
    List<RegistrationResponseDTO> getRegistrationsByEvent(Long eventId);
    List<RegistrationResponseDTO> getRegistrationsByUser(Long userId);
    RegistrationResponseDTO cancelRegistration(Long id);
    void deleteRegistration(Long id);
}
