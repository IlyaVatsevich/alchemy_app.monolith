package com.example.alchemy_app.security;

import com.example.alchemy_app.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder  {

    public UserDetailsImpl getUserDetails(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser() {
        return getUserDetails().getUser();
    }

}
