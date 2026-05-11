package com.eventmanagement.service;

import com.eventmanagement.dto.request.FeedbackRequestDTO;
import com.eventmanagement.dto.response.FeedbackResponseDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackResponseDTO submitFeedback(FeedbackRequestDTO requestDTO);
    List<FeedbackResponseDTO> getFeedbackByEvent(Long eventId);
    Double getAverageRating(Long eventId);
    void deleteFeedback(Long id);
}
