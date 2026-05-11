package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.VenueRequestDTO;
import com.eventmanagement.dto.response.VenueResponseDTO;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.VenueRepository;
import com.eventmanagement.service.VenueService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final DTOMapper mapper;

    @Override
    public VenueResponseDTO createVenue(VenueRequestDTO requestDTO) {
        Venue venue = Venue.builder()
                .name(requestDTO.getName())
                .location(requestDTO.getLocation())
                .capacity(requestDTO.getCapacity())
                .amenities(requestDTO.getAmenities())
                .pricePerDay(requestDTO.getPricePerDay())
                .build();
        return mapper.toVenueResponseDTO(venueRepository.save(venue));
    }

    @Override
    public Page<VenueResponseDTO> getAllVenues(Pageable pageable) {
        return venueRepository.findAll(pageable).map(mapper::toVenueResponseDTO);
    }

    @Override
    public VenueResponseDTO getVenueById(Long id) {
        return mapper.toVenueResponseDTO(venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id)));
    }

    @Override
    public List<VenueResponseDTO> filterByMinCapacity(Integer minCapacity) {
        return venueRepository.findAll().stream()
                .filter(v -> v.getCapacity() >= minCapacity)
                .map(mapper::toVenueResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VenueResponseDTO updateVenue(Long id, VenueRequestDTO requestDTO) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));
        
        venue.setName(requestDTO.getName());
        venue.setLocation(requestDTO.getLocation());
        venue.setCapacity(requestDTO.getCapacity());
        venue.setAmenities(requestDTO.getAmenities());
        venue.setPricePerDay(requestDTO.getPricePerDay());
        
        return mapper.toVenueResponseDTO(venueRepository.save(venue));
    }

    @Override
    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venue not found with id: " + id);
        }
        venueRepository.deleteById(id);
    }
}
