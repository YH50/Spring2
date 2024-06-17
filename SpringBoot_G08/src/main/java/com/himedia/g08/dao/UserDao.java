package com.himedia.g08.dao;

import com.himedia.g08.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    // application.properties 에 있는 DB 연결 정보로 연결돼있는 JdbcTemplate 이
    // 스프링컨테이너에 이미 보관되어 있음.
    // JdbcTemplate : Springboot 가 이미 스프링 컨테이너에 bean 으로 미리 보관된 후 시작함.
    @Autowired
    private JdbcTemplate template;

    public List<UserDto> selectAll(){
        String sql = "select * from myuser";
        List<UserDto> list = template.query(
                sql,
                new BeanPropertyRowMapper<UserDto>(UserDto.class)
        );
        return list;
    }

}
