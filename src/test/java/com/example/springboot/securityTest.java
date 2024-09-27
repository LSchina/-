package com.example.springboot;

import com.example.springboot.security.MyUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class securityTest {

    @Autowired
    MyUserDetailServiceImpl userDetailService;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void securityTest(){
        String demoPass = "$2a$10$QdVRbPQyVmJBV9CJWA1opOAy1HdJ52Gn7L7q99v38wC4cLLBAJI12";
        boolean matches = bCryptPasswordEncoder.matches("123", demoPass);
        System.out.println(matches);
    }
}
