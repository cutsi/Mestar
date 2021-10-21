package com.example.demo.Requests;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRequest {
    private String name;
    private String lastname;
    private String phone;
    private String address;
    private String email;
}
