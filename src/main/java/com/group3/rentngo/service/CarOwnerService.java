package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.CarOwner;

import java.util.List;

public interface CarOwnerService {
    CarOwner findCarOwnerByEmail(String email);
    CarOwner findCarOwnerByIdUser(long id);
    CarOwner findCarOwnerByPhone(String phone);
    List<CarOwner> findAll();
    void updateWallet(String email, String totalPrice);

}
