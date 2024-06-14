package com.himedia.g02;

//  현재 클래스가 클라이언트의 주소에서 요청을 받아서 기능별로 jsp까지 연결해주는 클래스

import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// 현재 클래스가 Controller 클래스임을 Annotation 으로 표시.
@Controller
public class SpringBootController {

    // 여기에 생성되는 메소드들이 기존 각 클래스들의 execute 메소드 역할을 함.
    // 각 요청은 역시 메소드에 붙은 Annotation 으로 구분.
    // 아레 메소드(main) : http://localhost:8070/ 라는 요청이 있을 시 실행
    //@RequestMapping(value = "/", method = RequestMethod.GET)

    @GetMapping("/")
    public String main( ){
        return "index";
        // 파일 이름과 경로 전체를 써서 포워딩 or sendRedirect 하지 않고
        // String 형식으로 파일이름만 리턴해서 /WEB-INF/views/ & .jsp 들이 조립되어 이동함
    }

    @GetMapping("/sub")
    public String sub(){
        return "sub";
    }

    @GetMapping("/direct")
    public @ResponseBody String direct(){
        return "<h1>오늘 롯데가 승리한다</h1>";
    }
}
