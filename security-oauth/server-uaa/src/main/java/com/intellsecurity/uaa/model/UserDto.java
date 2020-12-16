package com.intellsecurity.uaa.model;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String mobile;
}
