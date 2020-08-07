package com.project.shopmart.CommandLineRunner;

import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Override
    public void run(String...args) throws Exception {
        Optional<User> user = userRepository.findById("admin@gmail.com");
        if (user.isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setFirstName("Admin");
            admin.setLastName("ShopMart");
            admin.setAddress("500 El Camino Real, Santa Clara, CA, 95050, US");
            admin.setMobileNumber("123-123-1234");
            String hashedPassword= BCrypt.hashpw("Admin@123#",BCrypt.gensalt(10));
            admin.setPassword(hashedPassword);
            userRepository.save(admin);

        }
    }
}
