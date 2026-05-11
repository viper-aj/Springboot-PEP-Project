package com.eventmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    @NotNull
    private Long registrationId;

    @NotBlank
    private String ticketType;

    @NotNull
    @Min(0)
    private Double price;
}
