package com.intellsecurity.uaa.uaa.dao;

import com.intellsecurity.uaa.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    public UserDto getUserByusername(String username){
//        String sql="select id,username,password,mobile from t_user where username=?";
//        List<UserDto> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), new Object[]{username});
//        if(list !=null && list.size()>0){
//            return  list.get(0);
//        }
//        return null;
//    }

    /**
     * Oracle 版本
     * @param username
     * @return
     */
    public UserDto getUserByLogincode(String username){
        String sql="select a.id,a.id user_id,a.logincode,a.name username,a.name user_name,pwden password,PHONE mobile,c.id dept_id,c.name dept_name,d.id enterprise_id,d.name enterprise_name " +
                "from t_sys_user a left join t_sys_organization_user b on a.id=b.user_id " +
                "left join t_sys_organization c on b.dept_id=c.id " +
                "left join (select * from t_sys_organization where type=300 start with id in (select b.dept_id from t_sys_user a left join t_sys_organization_user b on a.id=b.user_id where a.logincode=?) connect by prior pid=id) d on 1=1 " +
                "where logincode=?";
        List<UserDto> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), new Object[]{username,username});
        if(list !=null && list.size()>0){
            return  list.get(0);
        }
        return null;
    }

    public UserDto getUserByusername(String username){
        String sql="select a.id,a.id user_id,a.logincode,a.name username,a.name user_name,pwden password,PHONE mobile,c.id dept_id,c.name dept_name,d.id enterprise_id,d.name enterprise_name " +
                "from t_sys_user a left join t_sys_organization_user b on a.id=b.user_id " +
                "left join t_sys_organization c on b.dept_id=c.id " +
                "left join (select * from t_sys_organization where type=300 start with id in (select b.dept_id from t_sys_user a left join t_sys_organization_user b on a.id=b.user_id where a.name=?) connect by prior pid=id) d on 1=1 " +
                "where name=?";
        List<UserDto> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), new Object[]{username,username});
        if(list !=null && list.size()>0){
            return  list.get(0);
        }
        return null;
    }
}
