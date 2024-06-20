package com.himedia.g13.Controller;

import com.himedia.g13.Dto.BoardDto;
import com.himedia.g13.Dto.MemberDto;
import com.himedia.g13.Dto.Paging;
import com.himedia.g13.Dto.ReplyDto;
import com.himedia.g13.Service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String insertBoard(@ModelAttribute("dto") @Valid BoardDto board,
                              BindingResult result, HttpServletRequest request, Model model) {

        String url = "board/insertBoardForm";
        if (result.getFieldError("pass") != null)
            model.addAttribute("message", result.getFieldError("pass").getDefaultMessage());
        else if (result.getFieldError("title") != null)
            model.addAttribute("message", result.getFieldError("title").getDefaultMessage());
        else if (result.getFieldError("content") != null)
            model.addAttribute("message", result.getFieldError("content").getDefaultMessage());
        else {
            bs.insertBoard(board);
            url = "redirect:/boardList";
        }

        return url;
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileupload")
    public String fileupload(
            @RequestParam("image") MultipartFile file,
            HttpServletRequest request, Model model) {
        String path = context.getRealPath("/upload");

        Calendar today = Calendar.getInstance();
        long t = today.getTimeInMillis();
        // 파일 이름 추출
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.lastIndexOf("."));  // abc.jsp >> abc
        String fn2 = filename.substring(filename.lastIndexOf(".") + 1);  // abc.jsp >> jsp
        String uploadPath = path + "/" + fn1 + t + "." + fn2;     // 저장경로 + abc123456789.jsp

        System.out.println(uploadPath);

        try {
            file.transferTo(new File(uploadPath));  // File Upload
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("image", filename);  // 사용자가 선택한 파일 이름
        model.addAttribute("savefilename", fn1 + t + "." + fn2);
        // 서버에 저장되는 이름

        return "board/completeUpload";
    }

    @GetMapping("/boardView")
    public ModelAndView boardView(@RequestParam("num") int num, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        HashMap<String, Object> result = bs.boardView(num);
        mav.addObject("board", result.get("board"));
        mav.addObject("replyList", result.get("replyList"));
        mav.setViewName("board/boardView");
        return mav;
    }

    @GetMapping("/boardViewWOCnt")
    public ModelAndView boardViewWOCnt(@RequestParam("num") int num, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        HashMap<String, Object> result = bs.boardViewWOCnt(num);
        mav.addObject("board", result.get("board"));
        mav.addObject("replyList", result.get("replyList"));
        mav.setViewName("board/boardView");
        return mav;
    }

    @PostMapping("/insertReply")
    public String insertReply(ReplyDto rdto, HttpServletRequest request) {
        bs.insertReply(rdto);
        return "redirect:/boardViewWOCnt?num=" + rdto.getBoardnum();
    }

    @GetMapping("/deleteReply")
    public String deleteReply(@RequestParam("replynum") int replynum,
                              @RequestParam("boardnum") int boardnum, HttpServletRequest request) {
        bs.deleteReply(replynum);
        return "redirect:/boardViewWOCnt?num=" + boardnum;

    }

    @GetMapping("/updateBoardForm")
    public ModelAndView updateBoardForm(@RequestParam("num") int num) {
        ModelAndView mav = new ModelAndView();
        BoardDto bdto = bs.getBoard(num);
        mav.addObject("board", bdto);
        mav.setViewName("board/updateBoardForm");
        return mav;
    }

    @PostMapping("/updateBoard")
    public String updateBoard(@ModelAttribute("dto") @Valid BoardDto bdto, Model model,
                              HttpServletRequest request, BindingResult result,
                              @RequestParam(value = "oldimage", required = false) String oldimage,
                              @RequestParam(value = "oldsavefilename", required = false) String oldsavefilename) {
        String url = "board/updateBoardForm";
        BoardDto bbdto = bs.getBoard(bdto.getNum());
        model.addAttribute("board", bbdto);

        if (result.getFieldError("title") != null)
            model.addAttribute("message", "제목은 필수 입력사항임!!");
        else if (result.getFieldError("content") != null)
            model.addAttribute("message", "게시물 내용 비우지 마셈!!");
        else {
            url = "redirect:/boardViewWOCnt?num=" + bdto.getNum();
            if (bdto.getSavefilename() == null || bdto.getSavefilename().equals("")) {
                bdto.setImage(oldimage);
                bdto.setSavefilename(oldsavefilename);
            }
            bs.updateBoard(bdto);
        }
        return url;
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("num") int num){
        bs.deleteBoard(num);
        return "redirect:/boardList";
    }
}
