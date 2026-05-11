package com.eventmanagement.controller;

import com.eventmanagement.dto.request.VenueRequestDTO;
import com.eventmanagement.dto.response.VenueResponseDTO;
import com.eventmanagement.service.VenueService;
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
@RequestMapping("/api/venues")
@RequiredArgsConstructor
@Tag(name = "Venues", description = "Venue creation, filtering, and management")
public class VenueController {

    private final VenueService venueService;

    @Operation(summary = "Create new venue", description = "Adds a new venue to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venue created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<VenueResponseDTO> createVenue(@Valid @RequestBody VenueRequestDTO requestDTO) {
        return new ResponseEntity<>(venueService.createVenue(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all venues", description = "Retrieves a paginated list of all venues")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venues retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Page<VenueResponseDTO>> getAllVenues(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(venueService.getAllVenues(pageable));
    }

    @Operation(summary = "Get venue by ID", description = "Retrieves details of a specific venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueResponseDTO> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.getVenueById(id));
    }

    @Operation(summary = "Filter venues by capacity", description = "Retrieves a list of venues that have at least the specified minimum capacity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venues retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<VenueResponseDTO>> filterByMinCapacity(@RequestParam Integer minCapacity) {
        return ResponseEntity.ok(venueService.filterByMinCapacity(minCapacity));
    }

    @Operation(summary = "Update venue", description = "Updates details of an existing venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VenueResponseDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueRequestDTO requestDTO) {
        return ResponseEntity.ok(venueService.updateVenue(id, requestDTO));
    }

    @Operation(summary = "Delete venue", description = "Deletes a venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }
}
