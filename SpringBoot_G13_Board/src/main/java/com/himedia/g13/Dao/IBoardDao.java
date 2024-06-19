package com.himedia.g13.Dao;

import com.himedia.g13.Dto.BoardDto;
import com.himedia.g13.Dto.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardDao {
    int getAllCount();

    List<BoardDto> selectBoard(Paging paging);

    int getReplyCnt(int num);
}
