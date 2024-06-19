package com.himedia.g13.Service;

import com.himedia.g13.Dao.IBoardDao;
import com.himedia.g13.Dto.BoardDto;
import com.himedia.g13.Dto.Paging;
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
            board.setReadcount(cnt);
        }
        result.put("boardList", list);
        result.put("paging", paging);

        return result;
    }
}
