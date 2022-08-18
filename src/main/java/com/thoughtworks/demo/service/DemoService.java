package com.thoughtworks.demo.service;

import com.thoughtworks.demo.domain.Game;
import com.thoughtworks.demo.domain.VideoGame;
import com.thoughtworks.demo.handler.exception.GameNotFoundException;
import com.thoughtworks.demo.handler.exception.GameTypeNotMatchException;
import com.thoughtworks.demo.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {
    @Autowired
    private DemoRepository repository;

    public List<Game> getAllGames() {
        return repository.getAllGames();
    }

    public Integer addVideoGame(VideoGame game) {
        return repository.addVideoGame(game);
    }

    public String getVideoGameInstruction(Integer id) {
        Game game = repository.getGameById(id);
        if (game == null) {
            throw new GameNotFoundException();
        }
        if (game instanceof VideoGame) {
            return game.generateInstruction();
        } else {
            throw new GameTypeNotMatchException();
        }
    }
}
