package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.FeedbackRequestDTO;
import com.eventmanagement.dto.response.FeedbackResponseDTO;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Feedback;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.entity.RegistrationStatus;
import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.FeedbackRepository;
import com.eventmanagement.repository.RegistrationRepository;
import com.eventmanagement.repository.UserRepository;
import com.eventmanagement.service.FeedbackService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final DTOMapper mapper;

    @Override
    public FeedbackResponseDTO submitFeedback(FeedbackRequestDTO requestDTO) {
        Registration registration = registrationRepository.findByUserIdAndEventId(requestDTO.getUserId(), requestDTO.getEventId())
                .orElseThrow(() -> new IllegalStateException("User does not have a registration for this event"));

        if (registration.getStatus() != RegistrationStatus.CONFIRMED) {
            throw new IllegalStateException("User does not have a confirmed registration for this event");
        }

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Feedback feedback = Feedback.builder()
                .user(user)
                .event(event)
                .rating(requestDTO.getRating())
                .comment(requestDTO.getComment())
                .build();

        return mapper.toFeedbackResponseDTO(feedbackRepository.save(feedback));
    }

    @Override
    public List<FeedbackResponseDTO> getFeedbackByEvent(Long eventId) {
        return feedbackRepository.findByEventId(eventId).stream()
                .map(mapper::toFeedbackResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRating(Long eventId) {
        Double avg = feedbackRepository.findAverageRatingByEventId(eventId);
        return avg != null ? avg : 0.0;
    }

    @Override
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback not found with id: " + id);
        }
        feedbackRepository.deleteById(id);
    }
}
