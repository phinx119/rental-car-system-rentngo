package com.group3.rentngo.service.impl;

import com.group3.rentngo.common.CommonUtil;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {
    private final CarOwnerRepository carOwnerRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;
    private final CommonUtil commonUtil;

    @Autowired
    public CarOwnerServiceImpl(CarOwnerRepository carOwnerRepository,
                               UserRepository userRepository,
                               CarRepository carRepository,
                               FeedbackRepository feedbackRepository,
                               BookingRepository bookingRepository,
                               CommonUtil commonUtil) {
        this.carOwnerRepository = carOwnerRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.feedbackRepository = feedbackRepository;
        this.bookingRepository = bookingRepository;
        this.commonUtil = commonUtil;
    }

    /**
     * @author phinx
     * @description get car owner by email
     */
    @Override
    public CarOwner findCarOwnerByEmail(String email) {
        return carOwnerRepository.findByEmail(email);
    }

    /**
     * @author thiendd
     * @description get car owner by user id
     */
    @Override
    public CarOwner findCarOwnerByIdUser(long id) {
        return carOwnerRepository.findByUser_Id(id);
    }

    /**
     * @author phinx
     * @description get car owner by phone
     */
    @Override
    public CarOwner findCarOwnerByPhone(String phone) {
        return carOwnerRepository.findByPhone(phone);
    }

    /**
     * @author phinx
     * @description get car owner by car owner id
     */
    @Override
    public Optional<CarOwner> findCarOwnerById(Long carOwnerId) {
        return carOwnerRepository.findById(carOwnerId);
    }

    /**
     * @author phinx
     * @description get car owner list
     */
    @Override
    public List<CarOwner> findAll() {
        return carOwnerRepository.findAll();
    }

    /**
     * @author phinx
     * @description update car owner information
     */
    @Override
    public void updateProfile(UpdateProfileDto updateProfileDto) throws ParseException {
        String name = updateProfileDto.getName();
        Date dateOfBirth = commonUtil.parseDate(updateProfileDto.getDateOfBirth());
        String nationalId = updateProfileDto.getNationalId();
        String phone = updateProfileDto.getPhone();
        String address = updateProfileDto.getHouseNumberAndStreet()
                .concat("-")
                .concat(updateProfileDto.getWard())
                .concat("-")
                .concat(updateProfileDto.getDistrict())
                .concat("-")
                .concat(updateProfileDto.getCity());
        String drivingLicense = updateProfileDto.getDrivingLicense();
        Long id = updateProfileDto.getId();

        carOwnerRepository.updateProfile(name, dateOfBirth, nationalId, phone, address, drivingLicense, id);
    }

    /**
     * @author phinx
     * @description update car owner wallet
     */
    @Override
    public void updateWallet(String email, String totalPrice) {
        CarOwner carOwner = carOwnerRepository.findByEmail(email);
        BigDecimal wallet = carOwner.getWallet() == null ? BigDecimal.valueOf(0) : carOwner.getWallet();
        BigDecimal totalPriceDecimal = new BigDecimal(totalPrice);
        BigDecimal updatedWallet = wallet.add(totalPriceDecimal);
        carOwner.setWallet(updatedWallet);
        carOwnerRepository.updateWalletByEmail(updatedWallet, email);
    }

    /**
     * @author phinx
     * @description map data from dto to car owner
     */
    @Override
    public UpdateProfileDto getDtoFromCarOwner(CarOwner carOwner) {
        UpdateProfileDto updateProfileDto = new UpdateProfileDto();
        updateProfileDto.setId(carOwner.getId());

        List<Role> roles = (List<Role>) carOwner.getUser().getRoles();
        updateProfileDto.setRole(roles.get(0).getName());

        updateProfileDto.setName(carOwner.getName());
        updateProfileDto.setDateOfBirth(String.valueOf(carOwner.getDateOfBirth()));
        updateProfileDto.setNationalId(carOwner.getNationalId());
        updateProfileDto.setPhone(carOwner.getPhone());
        updateProfileDto.setEmail(carOwner.getEmail());

        if (carOwner.getAddress() != null) {
            String[] address = carOwner.getAddress().split("-");
            updateProfileDto.setCity(address[3]);
            updateProfileDto.setDistrict(address[2]);
            updateProfileDto.setWard(address[1]);
            updateProfileDto.setHouseNumberAndStreet(address[0]);
        }

        updateProfileDto.setDrivingLicense(carOwner.getDrivingLicense());
        return updateProfileDto;
    }

    @Override
    public String findCarByLicensePlate(String licensePlate) {
        return carRepository.findCarByLicensePlate(licensePlate);
    }

}
