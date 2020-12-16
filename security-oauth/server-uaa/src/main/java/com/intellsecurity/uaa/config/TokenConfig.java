package com.intellsecurity.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class TokenConfig {

    //令牌存储策略 暂时使用内存方式
    @Bean
    public TokenStore tokenStore(){
        return  new InMemoryTokenStore();
    }
}
