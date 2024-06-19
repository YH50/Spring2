package com.himedia.g13.Dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberDto {
    @NotEmpty(message = "아이디 입력하셈") @NotNull(message = "아이디 입력하셈")
    private String userid;

    @NotEmpty(message = "비밀번호 입력하셈") @NotNull(message = "비밀번호 입력하셈")
    private String pwd;

    @NotEmpty(message = "이름 입력하셈") @NotNull(message = "이름 입력하셈")
    private String name;

    @NotEmpty(message = "전화번호 입력하셈") @NotNull(message = "전화번호 입력하셈")
    private String phone;

    @NotEmpty(message = "이메일 입력하셈") @NotNull(message = "이메일 입력하셈")
    private String email;

    private String provider;
}
