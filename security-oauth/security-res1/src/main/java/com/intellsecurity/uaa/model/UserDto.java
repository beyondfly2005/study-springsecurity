package com.intellsecurity.uaa.model;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String mobile;

    private String logincode;
    private Long user_id;
    private String user_name;
    private Long dept_id;
    private String dept_name;
    private Long enterprise_id;
    private String enterprise_name;
}
