package com.group3.rentngo.service.impl;

import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private CarRepository carRepository;
    private FeedbackRepository feedbackRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              CustomerRepository customerRepository,
                              CarRepository carRepository,
                              FeedbackRepository feedbackRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.feedbackRepository = feedbackRepository;
    }
}
