package com.intellsecurity.uaa.dao;

import com.intellsecurity.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.dao.userDao
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/25 14:03
 */
@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDto getUserByusername(String username){
        String sql="select a.id,a.id user_id,a.logincode,a.name username,a.name user_name,pwden password,PHONE mobile,c.id dept_id,c.name dept_name,d.id enterprise_id,d.name enterprise_name " +
                "from t_sys_user a left join t_sys_organization_user b on a.id=b.user_id " +
                "left join t_sys_organization c on b.dept_id=c.id " +
                "left join (select * from t_sys_organization where type=300 start with id in (select b.dept_id from t_sys_user a left join t_sys_organization_user b on a.id=b.user_id where a.name=?) connect by prior pid=id) d on 1=1 " +
                "where a.name=?";
        List<UserDto> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), new Object[]{username,username});
        if(list !=null && list.size()>0){
            return  list.get(0);
        }
        return null;
    }
}
