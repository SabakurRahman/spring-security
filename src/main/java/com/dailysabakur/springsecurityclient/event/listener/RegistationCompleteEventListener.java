package com.dailysabakur.springsecurityclient.event.listener;

import com.dailysabakur.springsecurityclient.entity.User;

import com.dailysabakur.springsecurityclient.event.RegistationCompleteEvent;
import com.dailysabakur.springsecurityclient.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j

public class RegistationCompleteEventListener
        implements ApplicationListener<RegistationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistationCompleteEvent event) {

        //Create the varification token for the User With link

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token,user);


        //Send mail to User

        String url = event.getApplicationUrl()+"/verifyRegistation?token="+token;

        log.info("Click the link to verify your account: {}",url);

    }
}
