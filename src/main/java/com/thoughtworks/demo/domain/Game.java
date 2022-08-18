package com.thoughtworks.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Game {
    private Integer id;
    private String name;
    private LocalDate publishedDate;
    private Float price;

    public abstract String generateInstruction();
}
