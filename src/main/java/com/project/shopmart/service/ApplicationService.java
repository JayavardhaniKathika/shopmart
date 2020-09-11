package com.project.shopmart.service;


import com.project.shopmart.data.entity.Product;
import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.ProductRepository;
import com.project.shopmart.data.repository.UserRepository;
import com.project.shopmart.exceptions.EtAuthException;
import com.project.shopmart.exceptions.WrongInputException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ApplicationService {
    public final UserRepository userRepository;
    public final ProductRepository productRepository;
    @Autowired
    public ApplicationService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
    public List<Product> getAllProducts(){
        Iterable<Product>products= productRepository.findAll();

        List<Product> productList=new ArrayList<>();
        for(Product product:products){
            productList.add(product);
        }
        return productList;
    }
    public List<? extends Object> getSearchedProducts(String s){
        List<Product> products=getAllProducts();
        List<Product> searchedProducts=new ArrayList<>();
        if(s.length()==0){
            return products;
        }
        else{
            for(Product product:products){
                if(product.getProductName().contains(s)){
                    searchedProducts.add(product);
                }
            }
            if(searchedProducts.size()==0){
                throw new WrongInputException("No results found for the search");
            }
            return searchedProducts;

        }

    }
    public List<Object> getProducts(int from,int to, List<Object> products) throws WrongInputException{
        if(to<from || from<0){
            throw new WrongInputException("Wrong from and to Values");
        }

        if(to>products.size()){
            to=products.size();
        }
        if(from>products.size()){
            from=0;
        }
        List<Object> productList=new ArrayList<>();
        for(int i=from;i<to;i++){
            productList.add(products.get(i));
        }
        return productList;
    }

    public List<Product> sortProducts(String columnName,String type, List<Product> products) throws WrongInputException{

        if(columnName.equals("Id")){
            if(type.equals("desc")){
                Collections.reverse(products);
            }
            return products;
        }
        products.sort(new Comparator<Product>(){
            @Override
            public int compare(Product o1, Product o2) {

                if(columnName.equals("Name")){
                    return o1.getProductName().compareTo(o2.getProductName());
                }
                else if(columnName.equals("Price")){
                    return o1.getPrice().compareTo(o2.getPrice());
                }
                else if(columnName.equals("count")){
                    return o1.getCount()-o2.getCount();
                }
                return 0;
            }
        });
        if(type.equals("desc")){
            Collections.reverse(products);
        }
        return products;
    }



    public void sort (Class a,String sortfield, String sortType, List<Object> objectList){
        Collections.sort(objectList, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                try{
                    Field sortField=a.getDeclaredField(sortfield);
                    sortField.setAccessible(true);
                    Object val1=sortField.get(o1);
                    Object val2=sortField.get(o2);
                    if(val1 instanceof String && val2 instanceof String){
                        if(sortType.equals("asc")){
                            return ((String) val1).compareTo((String)val2);
                        }
                        else{
                            return ((String) val2).compareTo((String)val1);
                        }
                    }
                    else { //Numeric field
                        Number num1 = (Number)val1;
                        Number num2 = (Number)val2;

                        if (sortType.equals("asc")) {
                            if (num1.floatValue() > num2.floatValue())  {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            if (num2.floatValue() > num1.floatValue())  {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }



}
