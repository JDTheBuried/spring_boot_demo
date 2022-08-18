package com.thoughtworks.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class VideoGame extends Game {
    private List<String> platforms;

    @Builder
    public VideoGame(Integer id, String name, LocalDate publishedDate, Float price, List<String> platforms) {
        super(id, name, publishedDate, price);
        this.platforms = platforms;
    }

    @Override
    public String generateInstruction() {
        return "You need a(an) " + String.join("/", platforms)
                + " to run this game.";
    }
}
