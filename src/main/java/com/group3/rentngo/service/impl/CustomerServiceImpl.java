package com.group3.rentngo.service.impl;

import com.group3.rentngo.common.CommonUtil;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.repository.BookingRepository;
import com.group3.rentngo.repository.CustomerRepository;
import com.group3.rentngo.repository.UserRepository;
import com.group3.rentngo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommonUtil commonUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserRepository userRepository,
                               BookingRepository bookingRepository,
                               CommonUtil commonUtil) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commonUtil = commonUtil;
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * @author phinx
     * @description get customer by email
     */
    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * @author phinx
     * @description get customer by phone
     */
    @Override
    public Customer findCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    /**
     * @author phinx
     * @description get customer list
     */
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * @author phinx
     * @description update customer information
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

        customerRepository.updateProfile(name, dateOfBirth, nationalId, phone, address, drivingLicense, id);
    }

    /**
     * @author phinx
     * @description update customer wallet
     */
    @Override
    public void updateWallet(String email, String totalPrice) {
        Customer customer = customerRepository.findByEmail(email);
        BigDecimal wallet = customer.getWallet() == null ? BigDecimal.valueOf(0) : customer.getWallet();
        BigDecimal totalPriceDecimal = new BigDecimal(totalPrice);
        BigDecimal updatedWallet = wallet.add(totalPriceDecimal);
        customer.setWallet(updatedWallet);
        customerRepository.updateWalletByEmail(updatedWallet, email);
    }

    /**
     * @author phinx
     * @description map data from dto to customer
     */
    @Override
    public UpdateProfileDto getDtoFromCustomer(Customer customer) {
        UpdateProfileDto updateProfileDto = new UpdateProfileDto();
        updateProfileDto.setId(customer.getId());

        List<Role> roles = (List<Role>) customer.getUser().getRoles();
        updateProfileDto.setRole(roles.get(0).getName());

        updateProfileDto.setName(customer.getName());
        updateProfileDto.setDateOfBirth(String.valueOf(customer.getDateOfBirth()));
        updateProfileDto.setNationalId(customer.getNationalId());
        updateProfileDto.setPhone(customer.getPhone());
        updateProfileDto.setEmail(customer.getEmail());

        if (customer.getAddress() != null) {
            String[] address = customer.getAddress().split("-");
            updateProfileDto.setCity(address[3]);
            updateProfileDto.setDistrict(address[2]);
            updateProfileDto.setWard(address[1]);
            updateProfileDto.setHouseNumberAndStreet(address[0]);
        }

        updateProfileDto.setDrivingLicense(customer.getDrivingLicense());
        return updateProfileDto;
    }
}
