package com.project.shopmart.service;


import com.project.shopmart.data.entity.User;
import com.project.shopmart.Web.dto.UserRegistrationDto;

public interface UserService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

}
