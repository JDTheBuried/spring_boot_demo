package com.thoughtworks.demo.repository;

import com.thoughtworks.demo.domain.BoardGame;
import com.thoughtworks.demo.domain.Game;
import com.thoughtworks.demo.domain.VideoGame;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DemoRepository {
    private List<Game> games = List.of(
            VideoGame.builder()
                    .id(1)
                    .name("The Elder Scrolls V: Skyrim")
                    .price(59.99f)
                    .publishedDate(LocalDate.of(2011, 11, 11))
                    .platforms(List.of("PS3", "Xbox 360", "PC", "PS4", "Xbox One", "Nintendo Switch"))
                    .build(),
            VideoGame.builder()
                    .id(2)
                    .name("Mass Effect")
                    .price(59.99f)
                    .publishedDate(LocalDate.of(2007, 11, 20))
                    .platforms(List.of("PS3", "Xbox 360", "PC"))
                    .build(),
            BoardGame.builder()
                    .id(3)
                    .name("Dungeons & Dragons")
                    .price(10f)
                    .publishedDate(LocalDate.of(1974, 1, 26))
                    .minAmountOfPlayers(2)
                    .maxAmountOfPlayers(7)
                    .build()
    );

    public List<Game> getAllGames() {
        return games;
    }

    public Integer addVideoGame(VideoGame game) {
        game.setId(games.size() + 1);
        games.add(game);
        return games.size();
    }

    public Game getGameById(Integer id) {
        for (Game current : games) {
            if (current.getId().equals(id)) {
                return current;
            }
        }
        return null;
    }
}
