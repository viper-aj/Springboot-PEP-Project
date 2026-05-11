package com.eventmanagement.controller;

import com.eventmanagement.dto.request.EventRequestDTO;
import com.eventmanagement.dto.response.EventResponseDTO;
import com.eventmanagement.entity.EventStatus;
import com.eventmanagement.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "Event lifecycle — create, publish, cancel, filter")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Create new event", description = "Creates a new event in DRAFT status")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Organizer, Venue, or Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO requestDTO) {
        return new ResponseEntity<>(eventService.createEvent(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all events", description = "Retrieves a paginated list of all events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Page<EventResponseDTO>> getAllEvents(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @Operation(summary = "Get event by ID", description = "Retrieves details of a specific event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(summary = "Get events by organizer", description = "Retrieves a list of all events created by a specific organizer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventResponseDTO>> getEventsByOrganizer(@PathVariable Long organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }

    @Operation(summary = "Filter events", description = "Both status and categoryId params are optional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered events retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> filterEvents(
            @RequestParam(required = false) EventStatus status,
            @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(eventService.filterEvents(status, categoryId));
    }

    @Operation(summary = "Update event", description = "Updates details of an existing event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Event or related entities not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequestDTO requestDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, requestDTO));
    }

    @Operation(summary = "Publish event", description = "Requires venue to be assigned before publishing")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event published successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot publish event without a venue"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/publish")
    public ResponseEntity<EventResponseDTO> publishEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.publishEvent(id));
    }

    @Operation(summary = "Cancel event", description = "Changes event status to CANCELLED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<EventResponseDTO> cancelEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.cancelEvent(id));
    }

    @Operation(summary = "Delete event", description = "Deletes an event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
