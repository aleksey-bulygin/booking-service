package com.project.booking.dto;

import com.project.booking.entity.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

    private String value;

    public static TokenDto from(Token token) {
        return new TokenDto(token.getValue());
    }
}
