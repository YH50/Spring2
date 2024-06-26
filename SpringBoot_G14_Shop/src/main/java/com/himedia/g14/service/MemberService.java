package com.himedia.g14.service;

import com.himedia.g14.dao.IMemberDao;
import com.himedia.g14.dto.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    IMemberDao mdao;

    public MemberVO getMember(String userid) {
        MemberVO mvo = mdao.getMember(userid);
        return mvo;
    }

    public void insertMember(MemberVO mvo) {
        mdao.insertMember(mvo);
    }
}
