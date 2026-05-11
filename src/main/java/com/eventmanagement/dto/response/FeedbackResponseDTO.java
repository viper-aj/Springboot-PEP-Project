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
public class FeedbackResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private EventResponseDTO event;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
