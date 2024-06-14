package com.himedia.g05;

import com.himedia.g05.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestController {

    @Test
    public void testMethod() {

        // @AllArgsConstructor 사용
        // MemberDto mdto = new MemberDto("scott", "킴민지");

        // @Builder 사용
        MemberDto mdto = MemberDto.builder()
                .id("kang")
                .name("강해린")
                .build();
        System.out.println(mdto.getId());
        System.out.println(mdto.getName());
        System.out.println(mdto);
        // Builder 를 쓰면 빼먹은 항목에 null값이 나오면서 에러가 나지 않음.
        // + null 값 대신 임의로 지정한 디폴트값도 나오게 할 수 있음.
        // >> .name 을 지워버리면 강해린 말고 팜하니가 나옴.

    }
}
