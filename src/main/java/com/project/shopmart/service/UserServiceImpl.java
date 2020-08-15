package com.project.shopmart.service;


import com.project.shopmart.Web.dto.UserRegistrationDto;
import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    public final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        //user.setPassword(passwordEncoder.encode(registration.getPassword()));
        //user.setPassword(registration.getPassword());
        user.setPassword(BCrypt.hashpw(registration.getPassword(),BCrypt.gensalt(10)));
        user.setMobileNumber(registration.getMobileNumber());
        user.setAddress(registration.getAddress());
        user.setUserId(registration.getUserId());
        return userRepository.save(user);
    }

    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }*/

}
