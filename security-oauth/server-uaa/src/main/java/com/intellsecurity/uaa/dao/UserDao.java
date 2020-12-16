package com.intellsecurity.uaa.dao;

import com.intellsecurity.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserDto getUserByusername(String username){
        String sql="select id,username,password,mobile from t_user where username=?";
        List<UserDto> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), new Object[]{username});
        if(list !=null && list.size()>0){
            return  list.get(0);
        }
        return null;
    }
}
