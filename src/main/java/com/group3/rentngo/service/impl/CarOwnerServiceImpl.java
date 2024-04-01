package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {
    private CarOwnerRepository carOwnerRepository;
    private UserRepository userRepository;
    private CarRepository carRepository;

    @Autowired
    public CarOwnerServiceImpl(CarOwnerRepository carOwnerRepository,
                               UserRepository userRepository,
                               CarRepository carRepository) {
        this.carOwnerRepository = carOwnerRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public CarOwner findCarOwnerByEmail(String email) {
        return carOwnerRepository.findByEmail(email);
    }

    @Override
    public CarOwner findCarOwnerByPhone(String phone) {
        return carOwnerRepository.findByPhone(phone);
    }
}
