package com.group3.rentngo.service;

import com.group3.rentngo.model.dto.ResetPasswordDto;
import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.User;

import java.util.List;

public interface UserService {
    void saveAdmin(UserDto userDto);

    void saveUser(SignupDto signupDto);

    User findUserByEmail(String email);

    List<User> findAll();

    void sendComplexEmail(String username, String content);

    void resetPassword(ResetPasswordDto resetPasswordDto);
}
