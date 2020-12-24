package com.intellsecurity.uaa.filter;

import com.ac.security.uaa.utils.EncryptUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.intellsecurity.uaa.dto.UserDto;
import com.intellsecurity.uaa.model.ResultInfo;
import org.apache.commons.lang.StringUtils;
/*import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;*/
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zouyu
 * @description
 * @date 2020/5/14
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)){
            return ;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String,Object> jsonToken = new HashMap<>(requestParameters);
        if(authentication !=null) {
            //取用户身份
            String principal = userAuthentication.getName();
        }

        String token = httpServletRequest.getHeader("json_token");
        if(StringUtils.isNotEmpty(token)){
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            String principal = JSON.parseObject(json).getString("principal");
            /*UserDTO userDTO = new UserDTO();
            userDTO.setUsername(principal);*/
            UserDto userDTO = JSON.parseObject(principal, UserDto.class);
            //用户权限
            JSONArray authoritiesArray = JSON.parseObject(json).getJSONArray("authorities");
            String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
            //将用户信息和权限填充 到用户身份token对象中
          /*  UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDTO,null, AuthorityUtils.createAuthorityList(authorities));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            //将authenticationToken填充到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);*/

            filterChain.doFilter(httpServletRequest,httpServletResponse);
        } else {
            PrintWriter writer = null;
            OutputStreamWriter osw = null;
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setCode("404");
            resultInfo.setMsg("未找到token信息");
            try {
                osw = new OutputStreamWriter(httpServletResponse.getOutputStream(),
                        "UTF-8");
                writer = new PrintWriter(osw, true);
                String jsonStr = JSON.toJSONString(resultInfo);
                writer.write(jsonStr);
                writer.flush();
                writer.close();
                osw.close();
            } catch (UnsupportedEncodingException e) {
                logger.error("过滤器返回信息失败:" + e.getMessage(), e);
            } catch (IOException e) {
                logger.error("过滤器返回信息失败:" + e.getMessage(), e);
            } finally {
                if (null != writer) {
                    writer.close();
                }
                if (null != osw) {
                    osw.close();
                }
            }
            return;
        }

    }
}
