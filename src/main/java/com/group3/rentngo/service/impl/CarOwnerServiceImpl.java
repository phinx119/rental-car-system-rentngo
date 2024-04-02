package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Feedback;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {

    private CarOwnerRepository carOwnerRepository;
    private UserRepository userRepository;
    private CarRepository carRepository;
    private FeedbackRepository feedbackRepository;

    private static BookingRepository bookingRepository;

    @Autowired
    public CarOwnerServiceImpl(CarOwnerRepository carOwnerRepository, UserRepository userRepository, CarRepository carRepository, FeedbackRepository feedbackRepository, BookingRepository bookingRepository) {
        this.carOwnerRepository = carOwnerRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.feedbackRepository = feedbackRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public CarOwner findCarOwnerByEmail(String email) {
        return carOwnerRepository.findByEmail(email);
    }

    @Override
    public CarOwner findCarOwnerByPhone(String phone) {
        return carOwnerRepository.findByPhone(phone);
    }


    @Override
    public void addnewCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void updateCarInformation(Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Feedback> showFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks;
    }

    @Override
    public void changBookingStatus(int id) {
        bookingRepository.updateById(id);
    }


}
