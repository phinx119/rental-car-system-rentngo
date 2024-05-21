package com.group3.rentngo.service;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.entity.Car;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> listCarOfOwner(Long id);

    Optional<Car> findById(Long id);

    List<Car> findAllCar();

    Car getCarFromDto(CarDto carDto);

    CarDto getCarDtoFromCar(Car car);
    void addCar(CarDto carDto);

    public String storeFile(String rootPath, String saveLocation, MultipartFile file);

    void editCar(Car car, CarDto carDto);
    List<Car> findAvailableCars( LocalDateTime startDateTime,
                                 LocalDateTime endDateTime,
                                 String location);
}
