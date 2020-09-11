package com.project.shopmart.Web;

import com.project.shopmart.Constants;
import com.project.shopmart.data.LoginDAO;
import com.project.shopmart.data.entity.User;
import com.project.shopmart.data.repository.UserRepository;
import com.project.shopmart.exceptions.EtAuthException;
import com.project.shopmart.service.ApplicationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        return "login";
    }
    //@RequestMapping
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> user,
                                                        HttpServletRequest request, HttpServletResponse response){
        String email=(String)user.get("email");
        String password =(String) user.get("password");
        User getUSer;
        try {
            getUSer = applicationService.validateUser(email,password);
        } catch (EtAuthException ae){
            System.out.println("Failed to authenticate error "+ ae);
            Map<String,String> resp = new HashMap<String, String>();
            resp.put("status", "Failed");
            resp.put("reason", "UnAuthorized User");
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }

        Map<String, String>  tokenMap = generateJWTToken(getUSer);
        String token = tokenMap.get("token");


        // create a cookie

        Cookie cookie = new Cookie("Authorization", token);

        //add cookie to response
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie("userName", token);

        //add cookie to response
        response.addCookie(cookie1);

        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
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


    @PostMapping("/userPage/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (request.isRequestedSessionIdValid() && session != null) {
            session.invalidate();
        }
        for(Cookie cookie:request.getCookies()){
            if(cookie.getName().equals("Authorization")){
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }


        return new ResponseEntity<>("Cookie Deleted", HttpStatus.OK);
    }


    @GetMapping("/userPage")
    public String getUserPage(HttpServletRequest request){
        int userId=(Integer) request.getAttribute("userId");
        return "Authenticated! userId:"+userId;
    }
    @GetMapping("/userPage/dashboard")
    public String dashboard(Model model, HttpServletRequest request){
        //request.getCookies().
        return "dashboard";
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


    @GetMapping(value = "/userPage/inventory", produces = "application/json")
    public @ResponseBody String getInventory(@RequestHeader("Authorization") String bearerToken){
        // 1. check if the bearer token is valid.
        // 2. if valid - fetch inventoy list
        // 3. if not valid - throw unauthorized 401
        // 4. return list

        return "{\"status\": \"success\"}";




//        String userPage = "user";
//        // db check.
//        // if invalid username/password
//        if (loginDAO.getEmail().equals("pranav.kethe@gmail.com")) {
//            return "{\"status\": \"success\"}";
//        }
//
//        throw new UserNotAuthorizedException("Not Authorized");
    }


//    inventory
//    product_name, product_id, price, rating
//
//    /localhost:8080/inventory
//    1) pagination support  e.g. /localhost:8080/inventory?from=0&size=5 should return first 5 rows
//    2) sorting  e.g. /localhost:8080/inventory?sort=rating:desc
//    3) searching e.g. /localhost:8080/inventory?search="table"&from=0&size=5&sort=rating:asc



}
