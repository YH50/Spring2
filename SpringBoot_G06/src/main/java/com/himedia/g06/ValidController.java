package com.himedia.g06;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ValidController {

    @GetMapping("/")
    public String main() {
        return "startPage";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("dto") ContentDto contentdto, BindingResult result, Model model) {

        // 전송된 파라미터들을 검사하여 하나라도 비어있을 시 startPage.jsp로 되돌아가고
        // 정상 전송 완료 시 result.jsp 로 이동.

        // validation 기능이 있는 클래스를 생성 & 그 객체를 이용하여 검사
        // 이름 : "ContentValidator" : 자바의 Validator 인터페이스를 implement 처리한 클래스.

        ContentValidator cvd = new ContentValidator();
        cvd.validate(contentdto, result);
        // BindingResult result : 에러 제목(키값)과 내용(벨류값)을 담을 수 있는 객체
        // validator 의 멤버 메소드인 validate 가 contentdto 내용을 검사한 후
        // result에서 발생하는 오류의 내용을 담아주고, 리턴되지 않더라도 "call by reference" 이라서
        // validate 메소드에 넣어준 오류 내용을 현 위치에서도 result 라는 이름으로 확인 가능.

        model.addAttribute("content", contentdto);
        if(result.hasErrors()){
            //model.addAttribute("message", "writer와 content는 비어있으면 안된다");
            if(result.getFieldError("writer")!=null)
            model.addAttribute("message", "writer가 비어있음");
            else if(result.getFieldError("content")!=null)
            model.addAttribute("message", "content 비어있음");
            return "startPage";
        }else {
            return "result";
        }
    }
}
