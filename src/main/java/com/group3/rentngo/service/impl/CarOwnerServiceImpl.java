package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public CarOwner findCarOwnerByIdUser(long id) {
        return carOwnerRepository.findByUser_Id(id);
    }

    @Override
    public CarOwner findCarOwnerByPhone(String phone) {
        return carOwnerRepository.findByPhone(phone);
    }

    @Override
    public List<CarOwner> findAll() {
        return carOwnerRepository.findAll();
    }

    @Override
    public void updateWallet(String email, String totalPrice) {
        CarOwner carOwner = carOwnerRepository.findByEmail(email);
        BigDecimal wallet = carOwner.getWallet() == null ? BigDecimal.valueOf(0) : carOwner.getWallet();
        BigDecimal totalPriceDecimal = new BigDecimal(totalPrice);
        BigDecimal updatedWallet = wallet.add(totalPriceDecimal);
        carOwner.setWallet(updatedWallet);
        carOwnerRepository.updateWalletByEmail(updatedWallet, email);
    }
}
