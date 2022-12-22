package com.example.alchemy_app.dto;

import com.example.alchemy_app.annotation.user.ValidUserLogin;
import com.example.alchemy_app.annotation.user.ValidUserPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
@Schema(title = "UserLogin",description = "User for logging")
public class UserLogDto {

    @Schema(example = "user1")
    @ValidUserLogin
    @NotBlank(message = "Login must be filled.")
    @Length(min = 1,max = 50,message = "Login length must be between {min} and {max} characters.")
    private String login;

    @Schema(example = "P@ssword1")
    @ValidUserPassword
    @NotBlank(message = "Password must be filled.")
    @Length(min = 8,max = 20,message = "Password length must be between {min} and {max} characters.")
    private String password;

}
