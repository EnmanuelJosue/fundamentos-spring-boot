package com.fundamentos.springboot.fundamentos.configuration;

import com.fundamentos.springboot.fundamentos.caseuses.GetUser;
import com.fundamentos.springboot.fundamentos.caseuses.GetUserImplement;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {
    @Bean
    GetUser getUser(UserService userService){
        return new GetUserImplement(userService);
    }
}
