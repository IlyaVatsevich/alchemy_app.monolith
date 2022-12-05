package com.example.alchemy_app.service_unit;

import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.generator.UserDtoGeneratorUtil;
import com.example.alchemy_app.generator.UserGeneratorUtil;
import com.example.alchemy_app.mapper.UserMapper;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.service.impl.UserIngredientServiceImpl;
import com.example.alchemy_app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserIngredientServiceImpl userIngredientService;
    @Mock
    private UserRepository userRepository;
    private User user;
    private UserRegDto userRegDto;

    @BeforeEach
    void setup() {
        user = UserGeneratorUtil.createValidUser();
        user.setId(1L);
        userRegDto = UserDtoGeneratorUtil.createValidUserForReg();
    }

    @Test
    void testRegistrationShouldSaveUser() {
        given(userMapper.buildUser(userRegDto)).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        Assertions.assertDoesNotThrow(()->userService.registration(userRegDto));
    }

    @Test
    void testGetUserByMaxGoldShouldReturnUsers() {
        Page<HighScoreTable> highScoreTablesByMaxGold = new PageImpl<>(
                List.of(
                        new HighScoreTable("test", 1L, 2L),
                        new HighScoreTable("test", 2L, 1L)));
        given(userRepository.findUsersByMaxGold(isA(Pageable.class))).willReturn(highScoreTablesByMaxGold);
        Page<HighScoreTable> usersByMaxGold = userService.getUsersByMaxGold(Pageable.unpaged());
        Assertions.assertNotNull(usersByMaxGold);
        Assertions.assertEquals(2,usersByMaxGold.getContent().size());
    }

    @Test
    void testGetUsersByMaxUserIngredientCountShouldReturnUsers() {
        Page<HighScoreTable> highScoreTablesByCount = new PageImpl<>(
                List.of(
                        new HighScoreTable("test",5L,1L),
                        new HighScoreTable("test",4L,2L)));
        given(userRepository.findUsersByMaxUserIngredientCount(isA(Pageable.class))).willReturn(highScoreTablesByCount);
        Page<HighScoreTable> usersByMaxUserIngredientCount = userService.getUsersByMaxUserIngredientCount(Pageable.unpaged());
        Assertions.assertNotNull(usersByMaxUserIngredientCount);
        Assertions.assertEquals(2,usersByMaxUserIngredientCount.getContent().size());
    }

}
