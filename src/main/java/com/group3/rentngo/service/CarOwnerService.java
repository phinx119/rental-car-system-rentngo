package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Feedback;

import java.util.List;

public interface CarOwnerService {
    CarOwner findCarOwnerByEmail(String email);

    CarOwner findCarOwnerByPhone(String phone);

    void addnewCar(Car car);

    void updateCarInformation(Car car);

    List<Feedback> showFeedback();

    void changBookingStatus(int id);
}
