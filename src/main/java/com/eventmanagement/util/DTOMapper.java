package com.eventmanagement.util;

import com.eventmanagement.dto.response.*;
import com.eventmanagement.entity.*;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper {

    public UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) return null;
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public VenueResponseDTO toVenueResponseDTO(Venue venue) {
        if (venue == null) return null;
        return VenueResponseDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .location(venue.getLocation())
                .capacity(venue.getCapacity())
                .amenities(venue.getAmenities())
                .pricePerDay(venue.getPricePerDay())
                .build();
    }

    public CategoryResponseDTO toCategoryResponseDTO(Category category) {
        if (category == null) return null;
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public EventResponseDTO toEventResponseDTO(Event event) {
        if (event == null) return null;
        return EventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .status(event.getStatus())
                .capacity(event.getCapacity())
                .organizer(toUserResponseDTO(event.getOrganizer()))
                .venue(toVenueResponseDTO(event.getVenue()))
                .category(toCategoryResponseDTO(event.getCategory()))
                .createdAt(event.getCreatedAt())
                .build();
    }

    public RegistrationResponseDTO toRegistrationResponseDTO(Registration registration) {
        if (registration == null) return null;
        return RegistrationResponseDTO.builder()
                .id(registration.getId())
                .user(toUserResponseDTO(registration.getUser()))
                .event(toEventResponseDTO(registration.getEvent()))
                .registeredAt(registration.getRegisteredAt())
                .status(registration.getStatus())
                .build();
    }

    public TicketResponseDTO toTicketResponseDTO(Ticket ticket) {
        if (ticket == null) return null;
        return TicketResponseDTO.builder()
                .id(ticket.getId())
                .registration(toRegistrationResponseDTO(ticket.getRegistration()))
                .ticketType(ticket.getTicketType())
                .price(ticket.getPrice())
                .issuedAt(ticket.getIssuedAt())
                .build();
    }

    public FeedbackResponseDTO toFeedbackResponseDTO(Feedback feedback) {
        if (feedback == null) return null;
        return FeedbackResponseDTO.builder()
                .id(feedback.getId())
                .user(toUserResponseDTO(feedback.getUser()))
                .event(toEventResponseDTO(feedback.getEvent()))
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .createdAt(feedback.getCreatedAt())
                .build();
    }
}
