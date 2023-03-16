package com.dailysabakur.springsecurityclient.service;

import com.dailysabakur.springsecurityclient.entity.User;
import com.dailysabakur.springsecurityclient.entity.VerificationToken;
import com.dailysabakur.springsecurityclient.model.UserModel;
import com.dailysabakur.springsecurityclient.repository.UserRepository;
import com.dailysabakur.springsecurityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Override
    public User registerService(UserModel userModel) {
        User user = new User();
        user.setFristName(userModel.getFristName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPasword(passwordEncoder.encode(userModel.getPasword()));
        user.setRole("USER");

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user,token);

        verificationTokenRepository.save(verificationToken);


    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
       if(verificationToken == null){
           return "invalid";
       }

       User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
         if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime())<=0){
             verificationTokenRepository.delete(verificationToken);
             return "expired";

         }
         user.setEnabled(true);
         userRepository.save(user);

        return "valid";
    }
}
