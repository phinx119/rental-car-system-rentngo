package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.Feedback;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackRepository feedbackRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               BookingRepository bookingRepository) {
        this.feedbackRepository = feedbackRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Feedback> showFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks;
    }

}
