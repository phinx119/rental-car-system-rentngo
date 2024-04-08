package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Car> listCarOfOwner(Long id) {
        return carRepository.findByCarOwner_Id(id);
    }

    @Override
    public Optional<Car> findbyId(Long id) {
        return carRepository.findById(id);
    }
}
