package com.dailysabakur.springsecurityclient.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private String fristName;
    private  String lastName;
    private  String email;

    private String pasword;

    private String matchingPassword;
}
