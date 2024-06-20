package com.himedia.g13.Dao;

import com.himedia.g13.Dto.BoardDto;
import com.himedia.g13.Dto.Paging;
import com.himedia.g13.Dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardDao {
    int getAllCount();
    List<BoardDto> selectBoard(Paging paging);
    int getReplyCnt(int num);
    void insertBoard(BoardDto board);
    void plusOneReadCount(int num);
    BoardDto getBoard(int num);
    List<ReplyDto> selectReply(int num);
    void insertReply(ReplyDto rdto);
    void deleteReply(int replynum);
    void updateBoard(BoardDto bdto);
    void deleteBoard(int num);
}
