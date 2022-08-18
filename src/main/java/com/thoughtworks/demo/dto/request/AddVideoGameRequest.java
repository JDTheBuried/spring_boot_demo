package com.thoughtworks.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class AddVideoGameRequest {
    @NotNull
    private String name;
    @PastOrPresent
    private LocalDate publishedDate;
    private Float price;
    @NotNull
    private List<String> platforms;
}
