package com.himedia.g13.Dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.sql.Timestamp;


@Data
public class BoardDto {
    private int num;
    private String userid;
    @NotEmpty(message = "비밀번호 입력하이소(게시글 수정 및 삭제 시 필요)")
    @NotNull(message = "비밀번호 입력하이소(게시글 수정 및 삭제 시 필요)")
    private String pass;
    private String email;
    @NotEmpty(message = "제목 입력하이소")
    @NotNull(message = "제목 입력하이소")
    private String title;
    @NotEmpty(message = "내용 입력하이소")
    @NotNull(message = "내용 입력하이소")
    private String content;
    private int readcount;
    private Timestamp writedate;
    private String image;
    private String savefilename;
    private int replycnt;
}
