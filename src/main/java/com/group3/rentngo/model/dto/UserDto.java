package com.group3.rentngo.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.group3.rentngo.model.entity.User}
 */
@Value
public class UserDto implements Serializable {
    Long id;
}