package com.eventmanagement.controller;

import com.eventmanagement.dto.request.TicketRequestDTO;
import com.eventmanagement.dto.response.TicketResponseDTO;
import com.eventmanagement.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "Ticket retrieval and management")
public class TicketController {

    private final TicketService ticketService;

    @Operation(summary = "Create ticket manually", description = "Creates a ticket for a registration manually")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Registration not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO requestDTO) {
        return new ResponseEntity<>(ticketService.createTicket(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get ticket by ID", description = "Retrieves details of a specific ticket by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @Operation(summary = "Get ticket by registration", description = "Retrieves a ticket associated with a specific registration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found for registration"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<TicketResponseDTO> getTicketByRegistrationId(@PathVariable Long registrationId) {
        return ResponseEntity.ok(ticketService.getTicketByRegistrationId(registrationId));
    }

    @Operation(summary = "Delete ticket", description = "Deletes a ticket by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
