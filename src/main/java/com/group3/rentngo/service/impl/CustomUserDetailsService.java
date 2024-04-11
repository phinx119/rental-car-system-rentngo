package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.model.entity.User;
import com.group3.rentngo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null && user.isStatus()) {
            return new CustomUserDetails(user);
        }else{
            throw new UsernameNotFoundException("Invalid email or password.");
        }
    }
}
