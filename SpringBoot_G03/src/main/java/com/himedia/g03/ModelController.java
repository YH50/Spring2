package com.himedia.g03;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
public class ModelController {
    @GetMapping("/")        // 요청 주소가 http://localhost:8070 일때 실행되는 메소드
    public @ResponseBody String root(){
        return "<h1>최강 롯데 자이언츠</h1>";      // 화면에 보여질 내용이 String 으로 리턴됨
    }
    @GetMapping ("/test")
    public String test1(HttpServletRequest request, Model model){

        // SpringBoot 의 Controller >> 많은 중요한 요소들이 전달인수로 일괄 전달됨
        // 이들 중 필요한 요소를 매개변수로 선택적으로 받아서 사용이 가능함
        // request 도 마찬가지로 매개변수로 추가하여 사용 가능

        // 모델2 방식에서 request 에 데이터를 넣고 포워딩 했지만
        // 여기서는 request 에 데이터를 넣기만 하면 자동으로 전달됨
        request.setAttribute("name1", "윤동희");
        model.addAttribute("name2", "이민석");
        return "test";
    }

    @GetMapping("/test2")
        public ModelAndView test2(HttpServletRequest request){
        // ModelAndView : 전달자료도 저장하고 이동할 페이지도 지정해서
        // 한번에 파일이름의 리턴과 자료전달을 가능하게 하는 클래스
        // 현재 메소드는 그 객체를 리턴형으로 지정하고 있음
        ModelAndView mav = new ModelAndView();

        ArrayList<String> list = new ArrayList<String>();
        list.add("item1");
        list.add("item2");
        list.add("item3");

        mav.addObject("list", list);        // request.setAttribute랑 같은 역할

        // 이동할 jsp 페이지 지정
        mav.setViewName("myView");
        return mav;
    }
    String name1;
    String name2;

    @GetMapping("/test3")
    public String test3(HttpServletRequest request, Model model){
        // 메소드의 리턴이 jsp 파일이 아닌 다른 Request 를 요청해야 할 때
//        request.setAttribute("name1", "손호영");
//        model.addAttribute("name2", "유강남");
        name1 = "유강남";
        name2 = "손호영";
        return "redirect:/test4";
    }

    @GetMapping("/test4")
    public String test4(HttpServletRequest request, Model model){
//        String name1 = (String)request.getAttribute("name1");
//        String name2 = (String)model.getAttribute("name2");
//
//        request.setAttribute("name1", name1);
//        model.addAttribute("name2", name2);                       >> 안된다

        model.addAttribute("name1", name1);
        request.setAttribute("name2", name2);
        return "test4";
    }

}
