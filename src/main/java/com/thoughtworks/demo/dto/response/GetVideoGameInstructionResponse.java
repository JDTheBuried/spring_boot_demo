package com.thoughtworks.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetVideoGameInstructionResponse {
    private String instruction;
}
