package com.group3.rentngo.service.impl;

import com.group3.rentngo.repository.CarOwnerRepository;
import com.group3.rentngo.repository.CarRepository;
import com.group3.rentngo.repository.UserRepository;
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
}
