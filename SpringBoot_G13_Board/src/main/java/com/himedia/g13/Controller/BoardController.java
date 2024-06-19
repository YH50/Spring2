package com.himedia.g13.Controller;

import com.himedia.g13.Dto.BoardDto;
import com.himedia.g13.Dto.MemberDto;
import com.himedia.g13.Dto.Paging;
import com.himedia.g13.Service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService bs;

    @GetMapping("/boardList")
    public ModelAndView boardlist(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = bs.selectBoard(request);
            mav.addObject("boardList", result.get("boardList"));
            mav.addObject("paging", result.get("paging"));
            mav.setViewName("board/boardList");
        }
        return mav;
    }

    @GetMapping("/insertBoardForm")
    public String insertBoardForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") == null)
            return "member/loginForm";
        else
            return "board/insertBoardForm";
    }

    @GetMapping("/selectimg")
    public String selectimg() {
        return "board/selectimg";
    }

    @PostMapping("/insertBoard")
    public String insertBoard(@RequestParam("bdto") BoardDto bdto, HttpServletRequest request){
        // 여기 채워야하는데 어떠케 해야하는지 모루게쒀요....ㅠㅠㅠㅠㅠ



        return "redirect:/boardList";
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileupload")
    public String fileupload(
        @RequestParam("image") MultipartFile file,
        HttpServletRequest request, Model model){
        String path = context.getRealPath("/upload");

        Calendar today = Calendar.getInstance();
        long t = today.getTimeInMillis();
        // 파일 이름 추출
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.lastIndexOf("."));  // abc.jsp >> abc
        String fn2 = filename.substring(filename.lastIndexOf(".")+1);  // abc.jsp >> jsp
        String uploadPath = path + "/" + fn1 + t + "." +fn2;     // 저장경로 + abc123456789.jsp

        System.out.println(uploadPath);

        try {
            file.transferTo(new File(uploadPath));  // File Upload
        }catch (IllegalStateException e) { e.printStackTrace();
        }catch (IOException e) { e.printStackTrace();
        }
        model.addAttribute("image", filename);  // 사용자가 선택한 파일 이름
        model.addAttribute("savefilename", fn1 + t + "." + fn2);
        // 서버에 저장되는 이름

        return "board/completeUpload";
    }
}
