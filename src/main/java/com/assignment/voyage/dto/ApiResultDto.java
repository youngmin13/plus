package com.assignment.voyage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ApiResultDto {

    private String msg;
    private int statusCode;

    @Builder
    public ApiResultDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}