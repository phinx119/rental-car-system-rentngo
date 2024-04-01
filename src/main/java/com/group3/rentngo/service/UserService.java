package com.group3.rentngo.service;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.entity.User;

public interface UserService {
    void saveUser(SignupDto signupDto);

    User findUserByUsername(String username);
}
