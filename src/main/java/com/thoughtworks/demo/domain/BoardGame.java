package com.thoughtworks.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class BoardGame extends Game {
    private Integer minAmountOfPlayers;
    private Integer maxAmountOfPlayers;

    @Builder
    public BoardGame(Integer id, String name, LocalDate publishedDate, Float price, Integer minAmountOfPlayers,
                     Integer maxAmountOfPlayers) {
        super(id, name, publishedDate, price);
        this.minAmountOfPlayers = minAmountOfPlayers;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
    }

    @Override
    public String generateInstruction() {
        return "You need " + minAmountOfPlayers + " - " + maxAmountOfPlayers + " players to start this game.";
    }
}
