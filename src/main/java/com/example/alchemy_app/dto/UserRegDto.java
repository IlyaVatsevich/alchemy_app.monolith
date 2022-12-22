package com.example.alchemy_app.dto;

import com.example.alchemy_app.annotation.user.ValidExistUserLogin;
import com.example.alchemy_app.annotation.user.ValidExistUserMail;
import com.example.alchemy_app.annotation.user.ValidUserPassword;
import com.example.alchemy_app.annotation.user.ValidUserLogin;
import com.example.alchemy_app.annotation.user.ValidUserMail;
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
@Schema(title = "UserRegistration",description = "User for registration")
public class UserRegDto {

    @Schema(description = "user login",example = "user1")
    @ValidUserLogin
    @ValidExistUserLogin
    @NotBlank(message = "Login must be filled.")
    @Length(min = 1,max = 50,message = "Login length must be between {min} and {max} characters.")
    private String login;

    @Schema(description = "user mail",example = "user_1@yopmail.com")
    @ValidUserMail
    @ValidExistUserMail
    @NotBlank(message = "Mail must be filled.")
    @Length(min = 8,max = 50,message = "Mail length must be between {min} and {max} characters.")
    private String mail;

    @Schema(description = "user password",example = "P@ssword1")
    @ValidUserPassword
    @NotBlank(message = "Password must be filled.")
    @Length(min = 8,max = 20,message = "Password length must be between {min} and {max} characters.")
    private String password;

}
