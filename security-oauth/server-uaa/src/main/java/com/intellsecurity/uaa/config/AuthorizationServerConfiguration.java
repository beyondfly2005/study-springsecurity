package com.intellsecurity.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    //1 配置客户端详细信息服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()	//是由内存方式
                .withClient("c1")	//client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))
                .resourceIds("res1")
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")  //该client允许的授权类型 五种授权类型
                .scopes("all") //允许的授权范围
                .autoApprove(false)
                .redirectUris("http://www.baidu.com");
    }

    // 2 配置令牌访问服务
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);  //是否产生刷新令牌
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(7200); //令牌默认有效期2小时
        services.setRefreshTokenValiditySeconds(259200); //刷新令牌有效期三天
        return services;
    }

    //设置授权码模式的授权码如何存取，暂时采用内存方式
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return  new InMemoryAuthorizationCodeServices();
    }

    //3 配置令牌访问端点
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)  //认证管理器 如果采用密码模式 需要配置此项
                .authorizationCodeServices(authorizationCodeServices)  //授权码模式
                .tokenServices(tokenServices()) //令牌管理服务 不管什么模式都需要
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    //4 令牌端点的安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }
}
