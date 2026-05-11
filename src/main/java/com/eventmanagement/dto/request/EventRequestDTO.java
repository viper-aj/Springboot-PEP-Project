package com.eventmanagement.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @FutureOrPresent
    private LocalDateTime date;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    private Long organizerId;

    @NotNull
    private Long venueId;

    @NotNull
    private Long categoryId;
}
