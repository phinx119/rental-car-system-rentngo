package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.dto.CarDto;
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
    
    @Override
    public void addCar(CarDto carDto) {
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setName(carDto.getName());
        car.setNumberOfSeats(carDto.getNumberOfSeats());
        car.setProductionYear(carDto.getProductionYear());
        car.setTransmissionType(carDto.getTransmissionType());
        car.setFuelType(carDto.getFuelType());
        car.setMileage(carDto.getMileage());
        car.setFuelConsumption(carDto.getFuelConsumption());
        car.setBasePrice(carDto.getBasePrice());
        car.setDeposit(carDto.getDeposit());
        car.setAddress(carDto.getAddress());
        car.setDescription(carDto.getDescription());
        car.setAdditionalFunctions(carDto.getAdditionalFunctions());
        car.setTermOfUse(carDto.getTermOfUse());
        car.setRegistrationPaperPath(carDto.getRegistrationPaperPath());
        car.setCertificateOfInspection(carDto.getCertificateOfInspection());
        car.setInsurance(carDto.getInsurance());
        car.setCarOwner(carDto.getCarOwner());
        carRepository.save(car);
    }
}
