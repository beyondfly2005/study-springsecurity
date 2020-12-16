package com.beyondsoft.security.springmvc.service;

import com.beyondsoft.security.springmvc.model.AuthenticationRequest;
import com.beyondsoft.security.springmvc.model.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    UserDto authentication(AuthenticationRequest authenticationRequest);

}
