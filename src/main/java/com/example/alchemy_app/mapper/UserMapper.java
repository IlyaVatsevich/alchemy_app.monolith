package com.example.alchemy_app.mapper;

import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private static final int USER_START_GOLD = 100;
    private static final boolean IS_USER_ACTIVE = true;
    private static final Set<UserRole> USER_ROLE = Set.of(UserRole.USER);
    private final PasswordEncoder passwordEncoder;

    public User buildUser(UserRegDto userRegDto) {
        return User.builder().withGold(USER_START_GOLD).
                withLogin(userRegDto.getLogin()).
                withMail(userRegDto.getMail()).
                withPassword(passwordEncoder.encode(userRegDto.getPassword()).toCharArray()).
                withUserRole(USER_ROLE).
                withIsActive(IS_USER_ACTIVE).
                build();
    }
}
