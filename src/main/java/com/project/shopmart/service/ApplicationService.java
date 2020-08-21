package com.project.shopmart.service;


import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import com.project.shopmart.exceptions.EtAuthException;
import org.mindrot.jbcrypt.BCrypt;
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

    //returs list of all users except admin
    public List<User> findAllUsers(){
        Iterable<User> users=userRepository.findAll();

        List<User> userList=new ArrayList<>();
        for(User user:users){
            if(!user.getEmail().equals("admin@gmail.com"))
                userList.add(user);
        }
        return userList;
    }

    //returns list of all users including admin
    public List<User> findAll(){
        Iterable<User> users=userRepository.findAll();

        List<User> userList=new ArrayList<>();
        for(User user:users){
                userList.add(user);
        }
        return userList;
    }
    public User validateUser(String email, String password) throws EtAuthException {
        if(email!=null){


            List<User> userList=new ArrayList<>();
            userList=findAll();

            for(User user:userList){
                if(user.getEmail().equals(email) && BCrypt.checkpw(password,user.getPassword())){
                    return user;
                }
            }
            throw new EtAuthException("Invalid Email/password");

        }
        throw new EtAuthException("Invalid Email/password");

    }
    //tells if a users is there or nor
    //returns true if a user is present else false
    public boolean isUser(String id, List<User> userList){
        for(User user:userList){
            if(user.getEmail().equals(id)){
                return true;
            }
        }
        return false;
    }

    //returns all the details of user with a given id
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

    //Tells if user id and password are correct
    //credential evaluation
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
