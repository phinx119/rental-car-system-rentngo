package com.group3.rentngo.service;

import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarOwner;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CarOwnerService {
    CarOwner findCarOwnerByEmail(String email);

    CarOwner findCarOwnerByPhone(String phone);

    Optional<CarOwner> findCarOwnerById(Long carOwnerId);

    CarOwner findCarOwnerByIdUser(long id);

    List<CarOwner> findAll();

    void updateProfile(UpdateProfileDto updateProfileDto) throws ParseException;

    void updateWallet(String email, String totalPrice);

    UpdateProfileDto getDtoFromCarOwner(CarOwner carOwner);

    Car findCarByLicensePlate(String licensePlate);
}
