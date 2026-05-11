package com.eventmanagement.controller;

import com.eventmanagement.dto.request.RegistrationRequestDTO;
import com.eventmanagement.dto.response.RegistrationResponseDTO;
import com.eventmanagement.service.RegistrationService;
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
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
@Tag(name = "Registrations", description = "Register for events with auto-ticket generation")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Register for event", description = "Rejects if event is not published, full, or user already registered. Auto-generates a ticket.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered for event successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed, event is full, not published, or user already registered"),
            @ApiResponse(responseCode = "404", description = "User or Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<RegistrationResponseDTO> registerForEvent(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        return new ResponseEntity<>(registrationService.registerForEvent(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get registrations by event", description = "Retrieves all registrations for a specific event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registrations retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(registrationService.getRegistrationsByEvent(eventId));
    }

    @Operation(summary = "Get registrations by user", description = "Retrieves all registrations for a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registrations retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(registrationService.getRegistrationsByUser(userId));
    }

    @Operation(summary = "Cancel registration", description = "Cancels an existing registration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Registration not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<RegistrationResponseDTO> cancelRegistration(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.cancelRegistration(id));
    }

    @Operation(summary = "Delete registration", description = "Deletes a registration by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registration deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Registration not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
