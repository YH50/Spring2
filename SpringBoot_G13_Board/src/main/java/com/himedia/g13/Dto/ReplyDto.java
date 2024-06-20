package com.himedia.g13.Dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReplyDto {
    private int replynum;
    private int boardnum;
    private String userid;
    private Timestamp writedate;

    @NotEmpty(message = "내용 입력하이소")
    @NotNull(message = "내용 입력하이소")      // NotEmpty 안에 NotNull 포함 >> 안써도 됨!
    private String content;
}
