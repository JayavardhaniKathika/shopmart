package com.project.shopmart.Web;

import com.project.shopmart.Constants;
import com.project.shopmart.data.LoginDAO;
import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import com.project.shopmart.service.ApplicationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class WebController {
    @Autowired
    private ApplicationService applicationService;
    List<User> userList=new ArrayList();

    private Map<String,String> generateJWTToken(User user){
        long timestamp=System.currentTimeMillis();
        String token= Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp+Constants.TOKEN_VALIDITY))
                .claim("userId",user.getUserId())
                .claim("email",user.getEmail())
                .claim("firstName",user.getFirstName())
                .claim("lastName",user.getLastName())
                .compact();
        Map<String,String> map=new HashMap();
        map.put("token",token);
        return map;
    }

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
    //@RequestMapping
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> user){
        String email=(String)user.get("email");
        String password =(String) user.get("password");
        User getUSer;
        getUSer = applicationService.validateUser(email,password);


        return new ResponseEntity<>(generateJWTToken(getUSer), HttpStatus.OK);
//        userList=applicationService.findAll();
//        String userPage="admin";
//        String username=loginDAO.getEmail();
//        String password=loginDAO.getPassword();
//        boolean isUser=applicationService.isUser(username,userList);
//
//        if(isUser){
//            User user=applicationService.getUserById(username,userList);
//
//            boolean eval=applicationService.evaluateUser(username,password,user);
//
//            if(eval){
//                String type=applicationService.getUserType(username);
//
//                return type;
//            }
//        }

        //return "user";
    }


//    inventory
//    product_name, product_id, price, rating
//
//    /localhost:8080/inventory
//    1) pagination support  e.g. /localhost:8080/inventory?from=0&size=5 should return first 5 rows
//    2) sorting  e.g. /localhost:8080/inventory?sort=rating:desc
//    3) searching e.g. /localhost:8080/inventory?search="table"&from=0&size=5&sort=rating:asc

    @GetMapping("/userPage")
    public String getUserPage(HttpServletRequest request){
        int userId=(Integer) request.getAttribute("userId");
        return "Authenticated! userId:"+userId;
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
