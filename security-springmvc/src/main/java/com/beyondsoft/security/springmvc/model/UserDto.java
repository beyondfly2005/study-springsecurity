package com.beyondsoft.security.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String moble;

}
