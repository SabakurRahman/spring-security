package com.dailysabakur.springsecurityclient.controller;

import com.dailysabakur.springsecurityclient.entity.User;
import com.dailysabakur.springsecurityclient.event.RegistationCompleteEvent;
import com.dailysabakur.springsecurityclient.model.UserModel;

import com.dailysabakur.springsecurityclient.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistationController {

   @Autowired
   private UserService userService;
   @Autowired
   private ApplicationEventPublisher publisher;

   @PostMapping("/register")
    public String regiserUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerService(userModel);

        publisher.publishEvent(new RegistationCompleteEvent(
                user,
                applicationUrl(request)
        ));

        return "success";



    }

    @GetMapping("/verifyRegistation")
    public String verifyRegistation(@RequestParam("token") String token){
       String result = userService.validateVerificationToken(token);
       if(result.equalsIgnoreCase("valid")){
           return "user verify successfully";
       }
       return "Bad User";

    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()
                +request.getContextPath();
    }

    @GetMapping("/hello")
    public  String hello(){
        return "Hello Shakib";
    }
}
