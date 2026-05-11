package com.eventmanagement.controller;

import com.eventmanagement.dto.request.FeedbackRequestDTO;
import com.eventmanagement.dto.response.FeedbackResponseDTO;
import com.eventmanagement.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Submit and retrieve event feedback and ratings")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "Submit feedback", description = "User must have a CONFIRMED registration for the event")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Feedback submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed or user does not have a confirmed registration"),
            @ApiResponse(responseCode = "404", description = "User or Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<FeedbackResponseDTO> submitFeedback(@Valid @RequestBody FeedbackRequestDTO requestDTO) {
        return new ResponseEntity<>(feedbackService.submitFeedback(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get feedback by event", description = "Retrieves all feedback submitted for a specific event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedback retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByEvent(eventId));
    }

    @Operation(summary = "Get average rating", description = "Returns average of all ratings (1-5) for the event as a Double")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Average rating retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/event/{eventId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long eventId) {
        return ResponseEntity.ok(feedbackService.getAverageRating(eventId));
    }

    @Operation(summary = "Delete feedback", description = "Deletes feedback by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Feedback deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Feedback not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}
