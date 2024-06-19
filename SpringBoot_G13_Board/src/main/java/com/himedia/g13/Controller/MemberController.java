package com.himedia.g13.Controller;

import com.google.gson.Gson;
import com.himedia.g13.Dto.KakaoProfile;
import com.himedia.g13.Dto.MemberDto;
import com.himedia.g13.Dto.OAuthToken;
import com.himedia.g13.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @GetMapping("/")
    public String root() {
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("dto") @Valid MemberDto memberdto,
                        BindingResult result, Model model, HttpServletRequest request) {
        String url = "member/loginForm";
        if (result.getFieldError("userid") != null) {
            model.addAttribute("message",
                    result.getFieldError("userid").getDefaultMessage());
        } else if (result.getFieldError("pwd") != null) {
            model.addAttribute("message",
                    result.getFieldError("pwd").getDefaultMessage());
        } else {
            // 정상 로그인 절차
            MemberDto mdto = ms.getMember(memberdto.getUserid());
            if (mdto == null)
                model.addAttribute("message", "아이디/비밀번호 확인해주삼!!");
            else if (!mdto.getPwd().equals(memberdto.getPwd()))
                model.addAttribute("message", "아이디/비밀번호 확인해주삼!!");
            else if (mdto.getPwd().equals(memberdto.getPwd())) {
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", mdto);
                url = "redirect:/boardList";
            } else
                model.addAttribute("message", "관리자 문의 ㄱㄱ");
        }
        return url;
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @GetMapping("/idcheck")
    public String idcheck(@RequestParam("userid") String userid, Model model) {
        MemberDto mdto = ms.getMember(userid);
        if (mdto == null) model.addAttribute("result", -1);
        else model.addAttribute("result", 1);
        model.addAttribute("userid", userid);
        return "member/idcheck";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("dto") @Valid MemberDto mdto, BindingResult result,
                       @RequestParam(value = "reid", required = false) String reid,
                       @RequestParam(value = "pwd_check", required = false) String pwd_check, Model model) {
        String url = "member/joinForm";
        if (result.getFieldError("userid") != null)
            model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
        else if (result.getFieldError("pwd") != null)
            model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
        else if (result.getFieldError("name") != null)
            model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
        else if (result.getFieldError("phone") != null)
            model.addAttribute("message", result.getFieldError("phone").getDefaultMessage());
        else if (result.getFieldError("email") != null)
            model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
        else if (reid == null || !mdto.getUserid().equals(reid))
            model.addAttribute("message", "아이디 중복체크 안하나!");
        else if (pwd_check == null || !mdto.getPwd().equals(pwd_check))
            model.addAttribute("message", "비밀번호 똑디 안맞추나!!");
        else {
            ms.insertMember(mdto);
            model.addAttribute("message", "가입완료. 로그인 하이소");
            url = "member/loginForm";
        }
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/updateMemberForm")
    public String updateMemberForm() {
        return "member/updateMemberForm";
    }

    @PostMapping("/updateMember")
    public String updateMember(@ModelAttribute("dto") @Valid MemberDto mdto, BindingResult result,
                               @RequestParam(value = "reid", required = false) String reid,
                               @RequestParam(value = "pwd_check", required = false) String pwd_check,
                               Model model, HttpServletRequest request) {
        String url = "member/updateMemberForm";
        if (result.getFieldError("pwd") != null)
            model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
        else if (result.getFieldError("name") != null)
            model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
        else if (result.getFieldError("phone") != null)
            model.addAttribute("message", result.getFieldError("phone").getDefaultMessage());
        else if (result.getFieldError("email") != null)
            model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
        else if (pwd_check == null || !mdto.getPwd().equals(pwd_check))
            model.addAttribute("message", "비밀번호 똑디 안맞추나!!");
        else {
            ms.updateMember(mdto);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", mdto);
            url = "redirect:/boardList";
        }
        return url;
    }

    @GetMapping("/deleteMember")
    public String deleteMember(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        MemberDto mdto = (MemberDto) session.getAttribute("loginUser");
        ms.deleteMember(mdto.getUserid());
        model.addAttribute("message", "회원탈퇴 완료됐십니더");
        return "member/loginForm";
    }

    @GetMapping("/kakaostart")
    public @ResponseBody String kakaostart(){
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=27697c88ada328eff0d085650caae76c"
                + "&redirect_uri=http://localhost:8070/kakaoLogin"
                + "&response_type=code'"
                + "</script>";
        return a;
    }
    @GetMapping("/kakaoLogin")
    public String login (HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        System.out.println(code);   // token 출력

        // 실제 User info 를 요청할 url & 전달인수 설정
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);    //import java.net.URL;  + add/throws 예외처리
        String bodyData = "grant_type=authorization_code";
        bodyData += "&client_id=27697c88ada328eff0d085650caae76c";
        bodyData += "&redirect_uri=http://localhost:8070/kakaoLogin";
        bodyData += "&code=" + code;

        // Stream 연결
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // Http header 값 넣기
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setDoOutput(true);

        // 인증절차 완료 후 User info 요청을 위한 정보를 요청 및 수신
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String input = "";
        StringBuilder sb = new StringBuilder();     // 조각난 String 을 조립해주는 객체
        while ((input = br.readLine()) != null) {
            sb.append(input);
            System.out.println(input);
        }
        //{"access_token":"gxS4XGepnL5DfbLB62aAGcb2mA30Jl-TAAAAAQo9dNoAAAGQLmvBqahuWkW__Nqy",
        // "token_type":"bearer",
        // "refresh_token":"eFudAvAF1JMocIvO34-0R24nIyznNka_AAAAAgo9dNoAAAGQLmvBpahuWkW__Nqy",
        // "expires_in":21599,"scope":"profile_nickname","refresh_token_expires_in":5183999}

        // 수신 내용을 GSon 으로 변경 (Parsing) & 준비된 객체에 옮겨 담기
        Gson gson = new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        // oAuthToken <- sb{"access_token":" 어쩌고저쩌고...
        // sb 내용을 항목에 맞춰서 OAuthToken 객체에 옮겨 담기

        // 실제 정보 요청 & 수신
        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);
        // import java.net.HttpURLConnection
        HttpsURLConnection conn2 = (HttpsURLConnection)url2.openConnection();
        // header 값 넣기
        conn2.setRequestProperty("Authorization", "Bearer "+oAuthToken.getAccess_token());
        conn2.setDoOutput(true);
        // Userinfo 수신
        BufferedReader br2 = new BufferedReader(
                new InputStreamReader(conn2.getInputStream(), "UTF-8")
        );
        String input2 = "";
        StringBuilder sb2 = new StringBuilder();
        while ((input2 = br2.readLine()) != null) {
            sb2.append(input2);
            System.out.println(input2);
        }
        // 수신 내용
        // {"id":3586445425,"connected_at":"2024-06-19T02:55:32Z",
        // "properties":{"nickname":"Yunho"},
        // "kakao_account":{"profile_nickname_needs_agreement":false,
        // "profile":{"nickname":"Yunho","is_default_nickname":false}}}
        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);

        System.out.println(kakaoProfile.getId());
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        System.out.println(ac.getEmail());
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println(pf.getNickname());

        MemberDto mdto = ms.getMember(kakaoProfile.getId());
        if (mdto == null) {
            mdto = new MemberDto();
            mdto.setUserid(kakaoProfile.getId());
            mdto.setEmail("kakao");
            mdto.setName(pf.getNickname());
            mdto.setProvider("kakao");
            mdto.setPwd("kakao");
            mdto.setPhone("");
            ms.insertMember(mdto);
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", mdto);
        return "redirect:/boardList";
    }
}
