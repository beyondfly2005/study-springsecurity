package com.intellsecurity.uaa.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //认证管理器
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //自定义密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHAMd5MixPasswordEncoder();
    }

    //密码编码器
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 必须配置，不然OAuth2的http配置不生效----不明觉厉
                .requestMatchers()
                .antMatchers("/auth/login", "/auth/authorize","/oauth/authorize")
                .and()
                .authorizeRequests()
                // 自定义页面或处理url是，如果不配置全局允许，浏览器会提示服务器将页面转发多次
                .antMatchers("/auth/login", "/auth/authorize")
                .permitAll()
                .anyRequest()
                .authenticated();

        // 表单登录
        http.formLogin()
                // 登录页面
                .loginPage("/auth/login")
                // 登录处理url
                .loginProcessingUrl("/auth/authorize");
        http.httpBasic().disable();

/*        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/r/r1").hasAnyAuthority()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login") //自定义登录页面
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/authentication/form")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/success")
                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll();

        //默认都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
        http.csrf().disable();*/

//                .antMatchers("/r/**").hasAnyAuthority() // /r/**的请求必须经过认证
//                //.antMatchers("/r/r1").hasAnyAuthority("p1")
//                .anyRequest().permitAll()  //除了/r/** 其他请求可以访问
//                .and()
//                .formLogin() //允许表单验证
//                .loginPage("/login-view") //登录页面
//                .successForwardUrl("/login-success") //自定义登录成功的页面地址
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and().logout()
//                .logoutUrl("/logout");
    }
}
