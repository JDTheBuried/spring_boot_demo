package com.thoughtworks.demo.dto.response;

import com.thoughtworks.demo.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GetAllGamesResponse {
    private List<Game> games;
}
