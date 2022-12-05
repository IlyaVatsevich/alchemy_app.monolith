package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.dto.UserLogDto;
import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.mapper.UserMapper;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.service.UserIngredientService;
import com.example.alchemy_app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserIngredientService userIngredientService;

    @Override
    @Transactional
    public void registration(UserRegDto userRegDto) {
        User newUser = userRepository.save(userMapper.buildUser(userRegDto));
        log.info("New user with id: " + newUser.getId() +  ", saved successfully.");
        userIngredientService.initializeUserIngredient(newUser);
    }

    @Override
    public String login(UserLogDto userLogDto) {
        //TODO after security add
        return null;
    }

    @Override
    public Page<HighScoreTable> getUsersByMaxGold(Pageable pageable) {
        return userRepository.findUsersByMaxGold(pageable);
    }

    @Override
    public Page<HighScoreTable> getUsersByMaxUserIngredientCount(Pageable pageable) {
        return userRepository.findUsersByMaxUserIngredientCount(pageable);
    }

    @Override
    public Page<UserIngredientDto> getUserIngredient(Pageable pageable) {
        //TODO after security add
        return null;
    }
}
