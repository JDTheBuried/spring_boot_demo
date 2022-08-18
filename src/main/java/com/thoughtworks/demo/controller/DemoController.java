package com.thoughtworks.demo.controller;

import com.thoughtworks.demo.domain.VideoGame;
import com.thoughtworks.demo.dto.request.AddVideoGameRequest;
import com.thoughtworks.demo.dto.response.AddVideoGameResponse;
import com.thoughtworks.demo.dto.response.GetAllGamesResponse;
import com.thoughtworks.demo.dto.response.GetVideoGameInstructionResponse;
import com.thoughtworks.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/game")
public class DemoController {

    @Autowired
    private DemoService service;

    @GetMapping
    public GetAllGamesResponse getAllGames() {
        return GetAllGamesResponse.builder()
                .games(service.getAllGames())
                .build();
    }

    @PostMapping("/video")
    public AddVideoGameResponse addVideoGame(@RequestBody @Valid AddVideoGameRequest request) {
        VideoGame game = VideoGame.builder()
                .id(0)
                .name(request.getName())
                .publishedDate(request.getPublishedDate())
                .price(request.getPrice())
                .platforms(request.getPlatforms())
                .build();
        return AddVideoGameResponse.builder()
                .id(service.addVideoGame(game))
                .build();
    }

    @GetMapping("/video/{id}/instruction")
    public GetVideoGameInstructionResponse getVideoGameInstruction(@PathVariable("id") Integer id) {
        return GetVideoGameInstructionResponse.builder()
                .instruction(service.getVideoGameInstruction(id))
                .build();
    }
}
