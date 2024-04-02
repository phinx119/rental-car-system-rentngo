package com.group3.rentngo.service.impl;

import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private CarOwnerRepository carOwnerRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          CarOwnerRepository carOwnerRepository,
                          BookingRepository bookingRepository) {
        this.carRepository = carRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.bookingRepository = bookingRepository;
    }
}
