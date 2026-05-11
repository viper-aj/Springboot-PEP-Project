package com.eventmanagement.dto.response;

import com.eventmanagement.entity.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private EventResponseDTO event;
    private LocalDateTime registeredAt;
    private RegistrationStatus status;
}
