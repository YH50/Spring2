package com.himedia.g13.Service;

import com.himedia.g13.Dao.IBoardDao;
import com.himedia.g13.Dto.BoardDto;
import com.himedia.g13.Dto.Paging;
import com.himedia.g13.Dto.ReplyDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    IBoardDao bdao;

    public HashMap<String, Object> selectBoard(HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<String, Object>();
        HttpSession session = request.getSession();

        // paging 객체 작업
        int page = 1;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", page);
        }else if(session.getAttribute("page") != null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }
        Paging paging = new Paging();
        paging.setPage(page);

        int count = bdao.getAllCount();
        paging.setTotalCount(count);
        paging.calPaging();
        paging.setStartNum(paging.getStartNum()-1);
        List<BoardDto> list = bdao.selectBoard(paging);

        for(BoardDto board : list){
            int cnt = bdao.getReplyCnt(board.getNum());
            board.setReplycnt(cnt);
        }
        result.put("boardList", list);
        result.put("paging", paging);

        return result;
    }

    public void insertBoard(BoardDto board) {
        bdao.insertBoard(board);
    }

    public HashMap<String, Object> boardView(int num) {
        HashMap<String, Object> result = new HashMap<>();

        // num 번호의 게시물 조회수 +1
        bdao.plusOneReadCount(num);

        // num 으로 게시물 조회
        BoardDto bdto = bdao.getBoard(num);

        // 댓글 조회
        List<ReplyDto> list = bdao.selectReply(num);

        // 게시물 & 댓글 HashMap 에 저장
        result.put("board", bdto);
        result.put("replyList", list);

        return result;
    }

    public HashMap<String, Object> boardViewWOCnt(int num) {
        HashMap<String, Object> result = new HashMap<>();

        // 조회수 올리는 코드만 빠졌음.

        BoardDto bdto = bdao.getBoard(num);
        List<ReplyDto> list = bdao.selectReply(num);
        result.put("board", bdto);
        result.put("replyList", list);

        return result;
    }

    public void insertReply(ReplyDto rdto) {
        bdao.insertReply(rdto);
    }

    public void deleteReply(int replynum) {
        bdao.deleteReply(replynum);
    }

    public BoardDto getBoard(int num) {
        return bdao.getBoard(num);
    }

    public void updateBoard(BoardDto bdto) {
        bdao.updateBoard(bdto);
    }

    public void deleteBoard(int num) {
        bdao.deleteBoard(num);
    }
}
