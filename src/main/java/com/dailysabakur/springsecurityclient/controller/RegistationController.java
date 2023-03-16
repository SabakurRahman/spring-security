package com.dailysabakur.springsecurityclient.controller;

import com.dailysabakur.springsecurityclient.entity.User;
import com.dailysabakur.springsecurityclient.entity.VerificationToken;
import com.dailysabakur.springsecurityclient.event.RegistationCompleteEvent;
import com.dailysabakur.springsecurityclient.model.UserModel;

import com.dailysabakur.springsecurityclient.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;



@RestController
@Slf4j
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
    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request){

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

        User user = verificationToken.getUser();
        resendEmailVerificationTokenMail(user,applicationUrl(request),verificationToken);

        return "Verification Link Sent";

    }

    private void resendEmailVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl+"/verifyRegistation?token="+verificationToken;

        log.info("Click the link to verify your account: {}",url);
    }


    @GetMapping("/test")
    public String test(){

       return "Working";

    }
}
