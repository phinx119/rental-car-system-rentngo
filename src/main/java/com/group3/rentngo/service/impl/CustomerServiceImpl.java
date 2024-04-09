package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.repository.BookingRepository;
import com.group3.rentngo.repository.CustomerRepository;
import com.group3.rentngo.repository.UserRepository;
import com.group3.rentngo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserRepository userRepository,
                               BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void updateWallet(String email, String totalPrice) {
        Customer customer = customerRepository.findByEmail(email);
        BigDecimal wallet = customer.getWallet() == null ? BigDecimal.valueOf(0) : customer.getWallet();
        BigDecimal totalPriceDecimal = new BigDecimal(totalPrice);
        BigDecimal updatedWallet = wallet.add(totalPriceDecimal);
        customer.setWallet(updatedWallet);
        customerRepository.updateWalletByEmail(updatedWallet, email);
    }
}
