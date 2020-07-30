package com.project.shopmart.service;


import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {
    public final UserRepository userRepository;
    @Autowired
    public ApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAllUsers(){
        Iterable<User> users=userRepository.findAll();
        List<User> userList=new ArrayList<>();
        for(User user:users){
            if(!user.getEmail().equals("admin@gmail.com"))
                userList.add(user);
        }
        return userList;
    }

}
