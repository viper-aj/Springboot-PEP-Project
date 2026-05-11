package com.eventmanagement.dto.response;

import com.eventmanagement.entity.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private EventStatus status;
    private Integer capacity;
    private UserResponseDTO organizer;
    private VenueResponseDTO venue;
    private CategoryResponseDTO category;
    private LocalDateTime createdAt;
}
