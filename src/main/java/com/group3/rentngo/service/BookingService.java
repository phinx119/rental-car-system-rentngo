package com.group3.rentngo.service;

import com.group3.rentngo.model.dto.BookingDto;
import com.group3.rentngo.model.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking findById(Long id);
    List<Booking> findAll(Long id);
    Booking findLastestBooking();
    void createNewBooking(BookingDto bookingDto);
    void changeBookingStatus(String status, Long id);
}
