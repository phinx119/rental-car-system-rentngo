package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarImage;
import com.group3.rentngo.repository.BookingRepository;
import com.group3.rentngo.repository.CarImageRepository;
import com.group3.rentngo.repository.CarOwnerRepository;
import com.group3.rentngo.repository.CarRepository;
import com.group3.rentngo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final BookingRepository bookingRepository;
    private final CarImageRepository carImageRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          CarOwnerRepository carOwnerRepository,
                          BookingRepository bookingRepository,
                          CarImageRepository carImageRepository) {
        this.carRepository = carRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.bookingRepository = bookingRepository;
        this.carImageRepository = carImageRepository;
    }

    /**
     * @author thiendd
     * @description
     */
    @Override
    public List<Car> listCarOfOwner(Long id) {
        return carRepository.findByCarOwner_Id(id);
    }

    /**
     * @author thiendd
     * @description
     */
    @Override
    public Optional<Car> findbyId(Long id) {
        return carRepository.findById(id);
    }

    /**
     * @author tiennq
     * @description
     */
    @Override
    public void addCar(CarDto carDto, CarImage carImage) {
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
        car.setCertificateOfInspectionPath(carDto.getCertificateOfInspectionPath());
        car.setInsurancePath(carDto.getInsurancePath());
        car.setCarImage(carImage);
        carRepository.save(car);
    }

    /**
     * @author tiennq
     * @description
     */
    @Override
    public void addCarImage(CarImage carImage) {
        carImageRepository.save(carImage);
    }

    public String storeFile(String saveLocation, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + originalFilename;
      try{
          File newFile = new File(saveLocation + "/" + newName);
          FileOutputStream fos = new FileOutputStream(newFile);
          fos.write(file.getBytes());
          fos.close();

      }
      catch (Exception e){
          System.out.println( e);
      }
        return saveLocation + "/" + newName;
    }


}
