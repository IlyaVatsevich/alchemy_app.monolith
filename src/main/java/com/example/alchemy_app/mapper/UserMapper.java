package com.example.alchemy_app.mapper;

import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {

    @Value("${user_pattern.start-gold}")
    private int startGold;

    @Value("${user_pattern.is-active}")
    private boolean isActive;

    private final PasswordEncoder passwordEncoder;

    public User buildUser(UserRegDto userRegDto) {
        return User.builder().withGold(startGold).
                withLogin(userRegDto.getLogin()).
                withMail(userRegDto.getMail()).
                withPassword(passwordEncoder.encode(userRegDto.getPassword()).toCharArray()).
                withUserRole(Set.of(UserRole.USER)).
                withIsActive(isActive).
                build();
    }
}
