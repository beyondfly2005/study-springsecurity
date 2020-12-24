package com.intellsecurity.uaa.uaa.model;

import lombok.Data;

@Data
public class PermissionDto {

    private Long id;

    private String code;

    private String description;

    private String url;
}
