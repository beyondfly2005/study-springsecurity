package com.beyondsoft.security.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //定义用户用户信息服务（查询用户信息）
    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhagnsan").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return manager;
    }

    //密码编码器(用来比对传入的密码和数据库密码的比对方式)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();  //不需要对输入的密码进行编码或者加密算法或摘要算法，只进行字符串比较
    }

    //配置拦截机制(最关键最关键)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()   //所有/r/**的请求必须认证通过
                .anyRequest().permitAll()       //除了/r/**的请求 其他请求都可以访问
                .and()
                .formLogin()   //运行表单登录
                .successForwardUrl("/login-success");//自定义登录成功的页面地址
    }
}
