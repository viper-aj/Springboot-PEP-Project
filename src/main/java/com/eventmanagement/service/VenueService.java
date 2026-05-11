package com.eventmanagement.service;

import com.eventmanagement.dto.request.VenueRequestDTO;
import com.eventmanagement.dto.response.VenueResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VenueService {
    VenueResponseDTO createVenue(VenueRequestDTO requestDTO);
    Page<VenueResponseDTO> getAllVenues(Pageable pageable);
    VenueResponseDTO getVenueById(Long id);
    List<VenueResponseDTO> filterByMinCapacity(Integer minCapacity);
    VenueResponseDTO updateVenue(Long id, VenueRequestDTO requestDTO);
    void deleteVenue(Long id);
}
