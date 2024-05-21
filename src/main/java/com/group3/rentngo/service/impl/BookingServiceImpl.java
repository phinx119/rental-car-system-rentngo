package com.group3.rentngo.service.impl;

import com.group3.rentngo.common.CommonUtil;
import com.group3.rentngo.model.dto.BookingDto;
import com.group3.rentngo.model.entity.*;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.BookingService;
import com.group3.rentngo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final CarRepository carRepository;
    private final FeedbackRepository feedbackRepository;
    private final CommonUtil commonUtil;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    public BookingServiceImpl(UserService userService,
                              BookingRepository bookingRepository,
                              CustomerRepository customerRepository,
                              CarOwnerRepository carOwnerRepository,
                              CarRepository carRepository,
                              FeedbackRepository feedbackRepository,
                              CommonUtil commonUtil,
                              PaymentHistoryRepository paymentHistoryRepository) {
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.carRepository = carRepository;
        this.feedbackRepository = feedbackRepository;
        this.commonUtil = commonUtil;
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Override
    public Booking findById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    @Override
    public List<Booking> findAll(Long id) {
        return bookingRepository.findByCustomer_Id(id);
    }

    @Override
    public Booking findLastestBooking() {
        UserDetails userDetails = userService.getUserDetail();
        Customer customer = customerRepository.findByEmail(userDetails.getUsername());
        return bookingRepository.findByCustomer_Id(customer.getId()).get(0);
    }

    /**
     * @author thiendd
     * @description insert new booking to database
     */
    @Override
    public void createNewBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        Date startDateTime = commonUtil.parseDateTime(bookingDto.getUpdateBookingDetailDto().getPickUpDate(), bookingDto.getUpdateBookingDetailDto().getPickUpTime());
        Date endDateTime = commonUtil.parseDateTime(bookingDto.getUpdateBookingDetailDto().getDropOffDate(), bookingDto.getUpdateBookingDetailDto().getDropOffTime());
        booking.setStartDateTime(startDateTime);
        booking.setEndDateTime(endDateTime);

        Customer customer = customerRepository.findByEmail(bookingDto.getCustomerDto().getEmail());
        booking.setCustomer(customer);

        Optional<Car> carOptional = carRepository.findById(bookingDto.getCarId());
        Car car = carOptional.orElse(null);
        booking.setCar(car);

        booking.setStatus("Pending");

        booking.setPaymentMethod(bookingDto.getPaymentMethod());
        if (bookingDto.getPaymentMethod().equals("Wallet")) {
            BigDecimal customerWallet = customer.getWallet().subtract(BigDecimal.valueOf(car.getDeposit()));
            customerRepository.updateWalletByEmail(customerWallet, customer.getEmail());

            CarOwner carOwner = car.getCarOwner();
            if (carOwner.getWallet() == null) {
                carOwner.setWallet(BigDecimal.ZERO);
            }
            BigDecimal carOwnerWallet = carOwner.getWallet().add(BigDecimal.valueOf(car.getDeposit()));
            carOwnerRepository.updateWalletByEmail(carOwnerWallet, carOwner.getEmail());

            bookingRepository.save(booking);

            Booking updateBooking = bookingRepository.findByCustomer_Id(customer.getId()).get(0);

            PaymentHistory paymentHistory1 = new PaymentHistory();
            paymentHistory1.setAmount(BigDecimal.valueOf(car.getDeposit()));
            paymentHistory1.setType("Pay deposit");
            paymentHistory1.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
            paymentHistory1.setUser(customer.getUser());
            paymentHistory1.setBooking(updateBooking);
            paymentHistoryRepository.save(paymentHistory1);

            PaymentHistory paymentHistory2 = new PaymentHistory();
            paymentHistory2.setAmount(BigDecimal.valueOf(car.getDeposit()));
            paymentHistory2.setType("Receive deposit");
            paymentHistory2.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
            paymentHistory2.setUser(carOwner.getUser());
            paymentHistory2.setBooking(updateBooking);
            paymentHistoryRepository.save(paymentHistory2);

//            Collection<PaymentHistory> paymentHistories = new ArrayList<>();
//            paymentHistories.add(paymentHistory1);
//            paymentHistories.add(paymentHistory2);
//            booking.setPaymentHistories(paymentHistories);
        } else {
            bookingRepository.save(booking);
        }
    }

    /**
     * @author thiendd
     * @description
     */
    @Override
    public void changeBookingStatus(String status, Long id) {
        bookingRepository.updateStatusById(status, id);
    }
}
