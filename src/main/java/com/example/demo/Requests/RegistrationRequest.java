package com.example.demo.Requests;

import com.example.demo.Models.AppUser;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private String password;
    private String phone;
    private final String address;
    public AppUser getUser(){
        return new AppUser(firstName,lastName,email,password,phone, address);
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
