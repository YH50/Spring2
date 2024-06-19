package com.himedia.g13.Dao;

import com.himedia.g13.Dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {
    MemberDto getMember(String userid);
    void insertMember(MemberDto mdto);

    void updateMember(MemberDto mdto);

    void deleteMember(String userid);
}
