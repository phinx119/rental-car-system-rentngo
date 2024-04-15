package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarImage;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.repository.BookingRepository;
import com.group3.rentngo.repository.CarImageRepository;
import com.group3.rentngo.repository.CarOwnerRepository;
import com.group3.rentngo.repository.CarRepository;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CarService;
import com.group3.rentngo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    private final UserService userService;
    private final CarOwnerService carOwnerService;
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(UserService userService,
                          CarOwnerService carOwnerService,
                          CarRepository carRepository){
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.carRepository = carRepository;
    }

    /**
     * @author thiendd
     * @description print list car of owner
     */
    @Override
    public List<Car> listCarOfOwner(Long id) {
        return carRepository.findByCarOwner_Id(id);
    }

    /**
     * @author thiendd
     * @description find car by id
     */
    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    /**
     * @author phinx
     * @description get car detail form car dto
     */
    @Override
    public Car getCarFromDto(CarDto carDto) {
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
        car.setAddress(carDto.getHouseNumberAndStreet()
                .concat("-")
                .concat(carDto.getWard())
                .concat("-")
                .concat(carDto.getDistrict())
                .concat("-")
                .concat(carDto.getCity()));
        car.setDescription(carDto.getDescription());
        car.setAdditionalFunctions(carDto.getAdditionalFunctions());
        car.setTermOfUse(carDto.getTermOfUse());
        car.setRegistrationPaperPath(carDto.getRegistrationPaperPath());
        car.setCertificateOfInspection(carDto.getCertificateOfInspection());
        car.setInsurance(carDto.getInsurance());
        car.setCarOwner(carDto.getCarOwner());
        car.setCertificateOfInspectionPath(carDto.getCertificateOfInspectionPath());
        car.setInsurancePath(carDto.getInsurancePath());
        car.setCarImage(carDto.getCarImage());
        return car;
    }

    /**
     * @author thiendd
     * @description tranfer car to carDto
     */
    @Override
    public CarDto getCarDtoFromCar(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setName(car.getName());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setColor(car.getColor());
        dto.setNumberOfSeats(car.getNumberOfSeats());
        dto.setProductionYear(car.getProductionYear());
        dto.setTransmissionType(car.getTransmissionType());
        dto.setFuelType(car.getFuelType());
        dto.setMileage(car.getMileage());
        dto.setFuelConsumption(car.getFuelConsumption());
        dto.setBasePrice(car.getBasePrice());
        dto.setDeposit(car.getDeposit());
        String address = car.getAddress();
        String[] arrAddress = address.split("[-]");
        dto.setHouseNumberAndStreet(arrAddress[0]);
        dto.setWard(arrAddress[1]);
        dto.setDistrict(arrAddress[2]);
        dto.setCity(arrAddress[3]);
        dto.setDescription(car.getDescription());
        dto.setAdditionalFunctions(car.getAdditionalFunctions());
        dto.setTermOfUse(car.getTermOfUse());

        dto.setRegistrationPaperPath(car.getRegistrationPaperPath());
        dto.setCertificateOfInspection(car.getCertificateOfInspection());
        dto.setInsurance(car.getInsurance());
        dto.setCarOwner(car.getCarOwner());
        dto.setCertificateOfInspectionPath(car.getCertificateOfInspectionPath());
        dto.setInsurancePath(car.getInsurancePath());
        dto.setCarImage(car.getCarImage());
        return dto;
    }

    /**
     * @author tiennq
     * @description
     */
    public String storeFile(String rootPath, String saveLocation, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + originalFilename;
        try {
            File newFile = new File(rootPath + saveLocation + "/" + newName);
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return saveLocation + "/" + newName;
    }


    /**
     * @author tiennq
     * @description insert new car
     */
    @Override
    public void addCar(CarDto carDto) {
        // set car owner for car
        UserDetails userDetails = userService.getUserDetail();
        CarOwner carOwner = carOwnerService.findCarOwnerByEmail(userDetails.getUsername());
        carDto.setCarOwner(carOwner);

        // set document image for car
        String rootPath = "src/main/resources/static";
        String saveLocation = "/images/document";
        carDto.setRegistrationPaperPath(storeFile(rootPath, saveLocation, carDto.getRegistrationPaper()));
        carDto.setCertificateOfInspectionPath(storeFile(rootPath, saveLocation, carDto.getCertificateOfInspection()));
        carDto.setInsurancePath(storeFile(rootPath, saveLocation, carDto.getInsurance()));

        // set image outlook image for car
        String saveLocationCarImage = "/images/car";
        CarImage carImage = new CarImage();
        carImage.setFrontImagePath(storeFile(rootPath, saveLocationCarImage, carDto.getFrontImage()));
        carImage.setBackImagePath(storeFile(rootPath, saveLocationCarImage, carDto.getBackImage()));
        carImage.setLeftImagePath(storeFile(rootPath, saveLocationCarImage, carDto.getLeftImage()));
        carImage.setRightImagePath(storeFile(rootPath, saveLocationCarImage, carDto.getRightImage()));
        carDto.setCarImage(carImage);

        Car car = getCarFromDto(carDto);

        carRepository.save(car);
    }

    /**
     * @author thiendd
     * @description edit infomation car
     */
    @Override
    public void editCar(Car car, CarDto carDto) {
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setNumberOfSeats(carDto.getNumberOfSeats());
        car.setProductionYear(carDto.getProductionYear());
        car.setTransmissionType(carDto.getTransmissionType());
        car.setFuelType(carDto.getFuelType());
        car.setMileage(carDto.getMileage());
        car.setFuelConsumption(carDto.getFuelConsumption());
        car.setBasePrice(carDto.getBasePrice());
        car.setDeposit(carDto.getDeposit());
        car.setAddress(carDto.getHouseNumberAndStreet()
                .concat("-")
                .concat(carDto.getWard())
                .concat("-")
                .concat(carDto.getDistrict())
                .concat("-")
                .concat(carDto.getCity()));
        car.setDescription(carDto.getDescription());
        carRepository.saveAndFlush(car);
    }
}
