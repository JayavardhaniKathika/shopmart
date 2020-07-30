package com.project.shopmart.Web;

import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import com.project.shopmart.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private ApplicationService applicationService;


    @GetMapping("/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return applicationService.findAllUsers();
    }

}
