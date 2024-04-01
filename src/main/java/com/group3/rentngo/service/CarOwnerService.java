package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.CarOwner;

public interface CarOwnerService {
    CarOwner findCarOwnerByEmail(String email);

    CarOwner findCarOwnerByPhone(String phone);
}
