package com.thoughtworks.demo.service;

import com.thoughtworks.demo.domain.BoardGame;
import com.thoughtworks.demo.domain.Game;
import com.thoughtworks.demo.domain.VideoGame;
import com.thoughtworks.demo.handler.exception.GameNotFoundException;
import com.thoughtworks.demo.handler.exception.GameTypeNotMatchException;
import com.thoughtworks.demo.repository.DemoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DemoServiceTests {

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

    private VideoGame newVideoGame = VideoGame.builder()
            .id(0)
            .name("Final Fantasy VII")
            .price(59.99f)
            .publishedDate(LocalDate.of(1997, 1, 31))
            .platforms(List.of("PS1", "PS4", "Xbox One", "PC", "Nintendo Switch", "iOS", "Android"))
            .build();

    @Mock
    private DemoRepository repository;
    @InjectMocks
    private DemoService service;

    @Test
    public void shouldGetAllGames() {
        when(repository.getAllGames()).thenReturn(games);
        List<Game> actual = service.getAllGames();
        assertEquals(games, actual);
        verify(repository).getAllGames();
    }

    @Test
    public void shouldAddNewVideoGame() {
        when(repository.addVideoGame(newVideoGame)).thenReturn(4);
        Integer actual = service.addVideoGame(newVideoGame);
        assertEquals(4, actual);
        verify(repository).addVideoGame(newVideoGame);
    }

    @Test
    public void shouldGetInstructionsForVideoGamesWhenIdIsValid() {
        String expected = "You need a(an) PS3/Xbox 360/PC/PS4/Xbox One/Nintendo Switch to run this game.";
        when(repository.getGameById(1)).thenReturn(games.get(0));
        String actual = service.getVideoGameInstruction(1);
        assertEquals(expected, actual);
        verify(repository).getGameById(1);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindSuchGame() {
        when(repository.getGameById(1)).thenReturn(null);
        assertThrows(GameNotFoundException.class, () -> service.getVideoGameInstruction(1));
        verify(repository).getGameById(1);
    }

    @Test
    public void shouldThrowExceptionWhenIsNotVideoGame() {
        when(repository.getGameById(1)).thenReturn(games.get(2));
        assertThrows(GameTypeNotMatchException.class, () -> service.getVideoGameInstruction(1));
        verify(repository).getGameById(1);
    }
}
