package com.himedia.g05.dto;

import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class MemberDto {
    private String id;

    @Builder.Default
    private String name="팜하니";
}
