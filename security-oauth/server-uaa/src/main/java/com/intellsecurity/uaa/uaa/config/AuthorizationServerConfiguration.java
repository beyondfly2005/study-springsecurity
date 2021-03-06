package com.intellsecurity.uaa.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //1 配置客户端详细信息服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //将客户端的信息存储到数据库
        clients.withClientDetails(clientDetailsService);
    /*    clients.inMemory()	//是由内存方式
                .withClient("c1")	//client_id
                //.secret(new BCryptPasswordEncoder().encode("secret")) //客户端秘钥
                .secret(new SHAMd5MixPasswordEncoder().encode("secret"))
                .resourceIds("res1") //客户端可以访问的资源列表
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")  //该client允许的授权类型 五种授权类型
                .scopes("all") //允许的授权范围
                .autoApprove(false)  //false 跳转到授权的页面 true 不需要跳转 直接发令牌
                .redirectUris("http://localhost:8082/ui-one/login/oauth2/code/custom");
     */
    }

    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    // 2 配置令牌访问服务
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService); //客户端信息服务
        services.setSupportRefreshToken(true);  //是否产生刷新令牌
        services.setTokenStore(tokenStore);  //令牌存储策略

        //设置令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);

        services.setAccessTokenValiditySeconds(7200); //令牌默认有效期2小时
        services.setRefreshTokenValiditySeconds(259200); //刷新令牌有效期三天
        return services;
    }

    //设置授权码模式的授权码如何存取，暂时采用内存方式
/*    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return  new InMemoryAuthorizationCodeServices();
    }*/

    //3 配置令牌访问端点
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;  //授权码模式

    @Autowired
    private AuthenticationManager authenticationManager;  //认证管理器

/*  //内存方式授权码
    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return new CustomTokenEnhancer();
    }*/

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);//设置授权码模式的授权码如何存取
    }

    @Autowired
    private TokenEnhancer customTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //将增强的token设置到增强链中
       /* TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
                                                                                 //accessTokenConverter
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer()));*/  //, jwtAccessTokenConverter()

        endpoints.authenticationManager(authenticationManager)  //认证管理器 如果采用密码模式 需要配置此项
            .authorizationCodeServices(authorizationCodeServices)  //授权码模式需要
            .tokenServices(tokenServices()) //令牌管理服务 不管什么模式都需要
            .allowedTokenEndpointRequestMethods(HttpMethod.POST)  //运行Post提交
            .pathMapping("/oauth/confirm_access","/custom/confirm_access")
//            .tokenEnhancer(enhancerChain)
        ;
        ;
    }

    //4 令牌端点的安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.tokenKeyAccess("permitAll()")  // /oauth/token公开
                .checkTokenAccess("permitAll()")  //检测令牌 /oauth/check_token公开
                .allowFormAuthenticationForClients();  //允许表单认证 申请令牌
    }
}
