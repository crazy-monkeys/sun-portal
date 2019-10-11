package com.crazy.portal.config.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 19:16 2019/4/20
 * @Modified by:
 */
@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

    public LoginAuthenticationFilter() {
        //拦截url为 "/user/login" 的POST请求
        super(new AntPathRequestMatcher("/user/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String loginName = "", loginPwd = "";
        if(StringUtils.hasText(body)) {
            JSONObject jsonObj = JSON.parseObject(body);
            String userNameStr = jsonObj.getString("loginName");
            String passStr = jsonObj.getString("loginPwd");

            if(userNameStr != null) loginName = userNameStr;
            if(passStr != null) loginPwd = passStr;
        }
        loginName = loginName.trim();
        //封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginName, loginPwd);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
