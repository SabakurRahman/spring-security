package com.dailysabakur.springsecurityclient.service;

import com.dailysabakur.springsecurityclient.entity.User;
import com.dailysabakur.springsecurityclient.entity.VerificationToken;
import com.dailysabakur.springsecurityclient.model.UserModel;
import org.hibernate.annotations.Comment;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public interface UserService {
    User registerService(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
