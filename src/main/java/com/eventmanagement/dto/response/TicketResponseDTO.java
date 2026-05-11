package com.eventmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private RegistrationResponseDTO registration;
    private String ticketType;
    private Double price;
    private LocalDateTime issuedAt;
}
