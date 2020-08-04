package com.project.shopmart.Web;

import com.project.shopmart.data.LoginDAO;
import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import com.project.shopmart.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private ApplicationService applicationService;
    List<User> userList=applicationService.findAll();


    @GetMapping("/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return applicationService.findAllUsers();
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginDAO",new LoginDAO());
        return "login";
    }
    @PostMapping("/login")
    public String getUser(@ModelAttribute LoginDAO loginDAO){
        String userPage;


        return "userPage";
    }

    @PostMapping("/addUser")
    public String user(@ModelAttribute User user){

        //userRepository.save(user);
        return "success";
    }
    @GetMapping("/admin")
    public String getAdminPage(Model model){

        return "admin";
    }



}
