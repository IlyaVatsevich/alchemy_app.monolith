package com.example.alchemy_app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class HighScoreTableImpl implements HighScoreTable {

    private String login;

    private Long score;

    private Long place;

}
