package com.example.alchemy_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class TokenRequest {

    @NotBlank(message = "Refresh token must be filled.")
    @Length(min = 36,max = 36,message = "Refresh token length must be {max}.")
    @Pattern(regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
            message = "Refresh token is not in the correct format")
    private String refreshToken;


}
