package com.himedia.g09.dao;

import com.himedia.g09.dto.BbsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BbsDao {

    @Autowired
    private JdbcTemplate template;

    public List<BbsDto> select() {
        String sql = "select * from bbs order by id desc";
        // 테이블의 필드명과 Dto 멤버변수명이 정확히 일치할 때
        List<BbsDto> list = template.query(sql,
                new BeanPropertyRowMapper<BbsDto>(BbsDto.class));

        // 테이블의 필드명과 Dto 멤버변수명이 정확히 일치하지 않을 때
        List<BbsDto> list2 = template.query(sql,
                new RowMapper<BbsDto>() {
                @Override
                    public BbsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BbsDto bdto = new BbsDto();
                    bdto.setId(rs.getInt("id"));
                    bdto.setWriter(rs.getString("writer"));
                    bdto.setContent(rs.getString("content"));
                    bdto.setTitle(rs.getString("title"));
                    return bdto;
                    // return 된 bdto 는 list 에 누적되어 쌓임
                    }
                }
        );
    return list;
    }

    public void insert(BbsDto bbsdto) {
        String sql = "insert into bbs(writer,content,title) values(?,?,?)";
        template.update(sql,bbsdto.getWriter(),bbsdto.getContent(),bbsdto.getTitle());

    }

    public BbsDto getBbs(int id) {
        String sql = "select * from bbs where id=?";
        // 방법 1 - template.query()
        List<BbsDto> list = template.query(sql,
                new BeanPropertyRowMapper<BbsDto>(BbsDto.class),
                id);
        BbsDto bdto1 = null;
        if(list.size()!=0) bdto1 = list.get(0);

        // 방법 2 - template.queryForObject()
        BbsDto bdto = template.queryForObject(sql,
                new BeanPropertyRowMapper<BbsDto>(BbsDto.class),
                id);
        return bdto;
        /*
        참고 : 방법 3
        List<BbsDto> list2 = template.query(
            sql,
            new RowMapper<BbsDto>() {
               @Override
               public BbsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                  BbsDto bdto = new BbsDto();
                  bdto.setId( rs.getInt("id") );
                  bdto.setWriter( rs.getString("writer") );
                  bdto.setContent( rs.getString("content") );
                  bdto.setTitle( rs.getString("title") );
                  return bdto;
               }
            }  , id
        );
        BbsDto bdto3=null;
        if( list2.size() != 0 ) bdto3 = list.get(0);

        */
    }

    public void update(BbsDto bdto) {
        String sql = "update bbs set writer=?,content=?,title=? where id=?";
        template.update(sql, bdto.getWriter(), bdto.getContent(),bdto.getTitle(),bdto.getId());
    }

    public void delete(int id) {
        String sql = "delete from bbs where id = ?";
        template.update(sql, id);
    }
}
