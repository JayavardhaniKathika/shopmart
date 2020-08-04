package com.project.shopmart.service;


import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<User> findAll(){
        Iterable<User> users=userRepository.findAll();

        List<User> userList=new ArrayList<>();
        for(User user:users){
                userList.add(user);
        }
        return userList;
    }
    public boolean isUser(String id, List<User> userList){
        for(User user:userList){
            if(user.getEmail().equals(id)){
                return true;
            }
        }
        return false;
    }
    public User getUserById(String id, List<User> userList){
        User existingUser=null;
        for(User user:userList){
            if(user.getEmail().equals(id)){
               existingUser=new User();
               existingUser.setEmail(user.getEmail());
               existingUser.setUserId(user.getUserId());
               existingUser.setPassword(user.getPassword());
               existingUser.setMobileNumber(user.getMobileNumber());
               existingUser.setAddress(user.getAddress());
               existingUser.setLastName(user.getLastName());
               existingUser.setFirstName(user.getFirstName());
               return user;
            }
        }
        return existingUser;
    }
    public boolean evaluateUser(String id,String pass,User user){
        if(user.getEmail().equals(id) && user.getPassword().equals(pass)){
            return true;
        }
        return false;
    }
    public String getUserType(String id){
        if(id.equals("admin@gmail.com")){
            return "admin";
        }
        else{
            return "user";
        }
    }



}
