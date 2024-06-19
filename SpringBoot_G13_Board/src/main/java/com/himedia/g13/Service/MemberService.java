package com.himedia.g13.Service;

import com.himedia.g13.Dao.IMemberDao;
import com.himedia.g13.Dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    IMemberDao mdao;

    public MemberDto getMember(String userid) {
        MemberDto mdto = mdao.getMember(userid);
        return mdto;
    }

    public void insertMember(MemberDto mdto) {
        mdao.insertMember(mdto);
    }

    public void updateMember(MemberDto mdto) {
        mdao.updateMember(mdto);
    }

    public void deleteMember(String userid) {
        mdao.deleteMember(userid);
    }
}
