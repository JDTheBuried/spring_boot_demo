package com.thoughtworks.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.demo.domain.BoardGame;
import com.thoughtworks.demo.domain.Game;
import com.thoughtworks.demo.domain.VideoGame;
import com.thoughtworks.demo.dto.request.AddVideoGameRequest;
import com.thoughtworks.demo.handler.exception.GameNotFoundException;
import com.thoughtworks.demo.handler.exception.GameTypeNotMatchException;
import com.thoughtworks.demo.service.DemoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DemoController.class)
public class DemoControllerTests {

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


    private VideoGame incompleteVideoGame = VideoGame.builder()
            .price(59.99f)
            .publishedDate(LocalDate.of(1997, 1, 31))
            .build();

    private VideoGame newVideoGameWithInvalidPublishDate = VideoGame.builder()
            .id(0)
            .name("Final Fantasy VII")
            .price(59.99f)
            .publishedDate(LocalDate.now().plus(1, ChronoUnit.DAYS))
            .platforms(List.of("PS1", "PS4", "Xbox One", "PC", "Nintendo Switch", "iOS", "Android"))
            .build();

    private BoardGame newBoardGame = BoardGame.builder()
            .id(0)
            .name("Warhammer 40K")
            .price(59.99f)
            .publishedDate(LocalDate.of(1987, 9, 30))
            .minAmountOfPlayers(1)
            .maxAmountOfPlayers(6)
            .build();

    private AddVideoGameRequest newVideoGameRequest = new AddVideoGameRequest("Final Fantasy VII",
            LocalDate.of(1997, 1, 31),
            59.99f,
            List.of("PS1", "PS4", "Xbox One", "PC", "Nintendo Switch", "iOS", "Android"));

    private String baseUrl = "/game";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DemoService service;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldGetAllGames() throws Exception {
        String expected = "{\"games\":[{\"id\":1,\"name\":\"The Elder Scrolls V: Skyrim\"," +
                "\"publishedDate\":\"2011-11-11\",\"price\":59.99," +
                "\"platforms\":[\"PS3\",\"Xbox 360\",\"PC\",\"PS4\",\"Xbox One\",\"Nintendo Switch\"]}," +
                "{\"id\":2,\"name\":\"Mass Effect\",\"publishedDate\":\"2007-11-20\",\"price\":59.99," +
                "\"platforms\":[\"PS3\",\"Xbox 360\",\"PC\"]},{\"id\":3,\"name\":\"Dungeons & Dragons\"," +
                "\"publishedDate\":\"1974-01-26\",\"price\":10.0,\"minAmountOfPlayers\":2,\"maxAmountOfPlayers\":7}]}";
        when(service.getAllGames()).thenReturn(games);
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(service).getAllGames();
    }

    @Test
    public void shouldAddVideoGameWhenRequestBodyIsValid() throws Exception {
        String expected = "{\"id\":4}";
        String requestBody = mapper.writeValueAsString(newVideoGameRequest);
        when(service.addVideoGame(newVideoGame)).thenReturn(4);
        mockMvc.perform(post(baseUrl + "/video")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(service).addVideoGame(newVideoGame);
    }

    @Test
    public void shouldThrowsExceptionWhenRequestBodyIsIncomplete() throws Exception {
        String requestBody = mapper.writeValueAsString(incompleteVideoGame);
        mockMvc.perform(post(baseUrl + "/video")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowsExceptionWhenPublishDateIsInvalid() throws Exception {
        String requestBody = mapper.writeValueAsString(newVideoGameWithInvalidPublishDate);
        mockMvc.perform(post(baseUrl + "/video")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowsExceptionWhenRequestBodyIsInvalid() throws Exception {
        String requestBody = mapper.writeValueAsString(newBoardGame);
        mockMvc.perform(post(baseUrl + "/video")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetInstructionsForVideoGamesWhenIdIsValid() throws Exception {
        String expected = "{\"instruction\":\"You need a(an) PS3/Xbox 360/PC/PS4/Xbox One/Nintendo Switch " +
                "to run this game.\"}";
        when(service.getVideoGameInstruction(1)).thenReturn("You need a(an) PS3/Xbox 360/" +
                "PC/PS4/Xbox One/Nintendo Switch to run this game.");
        mockMvc.perform(get(baseUrl + "/video/1/instruction"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
        verify(service).getVideoGameInstruction(1);
    }

    @Test
    public void shouldThrowsExceptionWhenIdIsInvalid() throws Exception {
        mockMvc.perform(get(baseUrl + "/video/a/instruction"))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void shouldSendGameNotFoundWhenNoSuchGame() throws Exception {
        String expected = "{\"message\":\"Game not found!\"}";
        when(service.getVideoGameInstruction(1)).thenThrow(new GameNotFoundException());
        mockMvc.perform(get(baseUrl + "/video/1/instruction"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expected));
        verify(service).getVideoGameInstruction(1);
    }
    @Test
    public void shouldSendGameTypeNotMatchWhenIdIsValid() throws Exception {
        String expected = "{\"message\":\"Game type not match!\"}";
        when(service.getVideoGameInstruction(1)).thenThrow(new GameTypeNotMatchException());
        mockMvc.perform(get(baseUrl + "/video/1/instruction"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expected));
        verify(service).getVideoGameInstruction(1);
    }
}
