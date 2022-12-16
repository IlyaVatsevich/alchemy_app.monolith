package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.dto.TokenRequest;
import com.example.alchemy_app.dto.TokenResponse;
import com.example.alchemy_app.dto.UserLogDto;
import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.entity.RefreshToken;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.exception.EntityNotExistException;
import com.example.alchemy_app.exception.PasswordNotCorrectException;
import com.example.alchemy_app.mapper.TokenMapper;
import com.example.alchemy_app.mapper.UserMapper;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.security.UserDetailsImpl;
import com.example.alchemy_app.service.RefreshTokenService;
import com.example.alchemy_app.service.UserIngredientService;
import com.example.alchemy_app.service.UserService;
import com.example.alchemy_app.security.JwtTokenUtilize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserIngredientService userIngredientService;
    private final TokenMapper tokenMapper;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtilize jwtTokenUtilize;

    @Override
    @Transactional
    public void registration(UserRegDto userRegDto) {
        User newUser = userRepository.save(userMapper.buildUser(userRegDto));
        log.info("New user with id - {} , saved successfully.",newUser.getId());
        userIngredientService.initializeUserIngredient(newUser);
    }

    @Override
    @Transactional
    public TokenResponse login(UserLogDto userLogDto) {
        UserDetails userDetails = loadUserByUsername(userLogDto.getLogin());
        if (!passwordEncoder.matches(userLogDto.getPassword(),userDetails.getPassword())) {
            throw new PasswordNotCorrectException("Password not correct.");
        }
        String refreshToken = refreshTokenService.createRefreshToken(((UserDetailsImpl) userDetails).getUser()).getToken();
        String accessToken = jwtTokenUtilize.generateAccessToken(userDetails.getUsername());
        logInfoAboutTokens(accessToken,refreshToken);
        return tokenMapper.mapTokens(accessToken, refreshToken);
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
        return userIngredientService.findUserIngredientsByUser(pageable);
    }

    @Override
    @Transactional
    public TokenResponse getNewAccessAndRefreshTokenByRefreshToken(TokenRequest tokenRequest) {
        RefreshToken oldRefreshToken = refreshTokenService.findByToken(tokenRequest.getRefreshToken());
        String accessToken = jwtTokenUtilize.generateAccessToken(oldRefreshToken.getUser().getLogin());
        String newRefreshToken = refreshTokenService.updateUsedRefreshToken(oldRefreshToken).getToken();
        logInfoAboutTokens(accessToken,newRefreshToken);
        return tokenMapper.mapTokens(accessToken,newRefreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username).
                orElseThrow(() -> new EntityNotExistException("User with such login: " + username + ", not exist."));
        return new UserDetailsImpl(user);
    }

    private void logInfoAboutTokens(String accessToken,String refreshToken) {
        log.debug("Access token created - {}",accessToken);
        log.debug("Refresh token created - {}",refreshToken);
    }
}
