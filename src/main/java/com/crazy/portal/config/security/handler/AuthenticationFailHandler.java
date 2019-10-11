package com.crazy.portal.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.util.ErrorCodes.CommonEnum;
import com.crazy.portal.util.ErrorCodes.SystemManagerEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 19:55 2019/4/20
 * @Modified by:
 */
@Slf4j
@Component
public class AuthenticationFailHandler implements AuthenticationFailureHandler{



    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException {

        try(ServletOutputStream os = response.getOutputStream()){
            response.reset();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=utf-8");
            BaseResponse baseResponse = getBaseResponse(e);
            os.write(JSON.toJSONString(baseResponse).getBytes());
        }
    }

    /**
     * 异常解析
     * @param e
     * @return
     */
    private BaseResponse getBaseResponse(AuthenticationException e) {
        //认证失败-Token过期
        if(e.getCause() instanceof NonceExpiredException){
            return new BaseResponse(SystemManagerEnum.TOKEN_INVALID.getCode(),SystemManagerEnum.TOKEN_INVALID.getMsg());
        }
        //认证失败-账号密码错误或者ad域认证失败
        else if(e instanceof BadCredentialsException || e.getCause() instanceof BadCredentialsException){
           return new BaseResponse(SystemManagerEnum.ACCOUNT_ERROR.getCode(),SystemManagerEnum.ACCOUNT_ERROR.getMsg());
        }
        //账户锁定
        else if(e instanceof LockedException || e.getCause() instanceof LockedException){
            if(e.getMessage().equals("password expiration")){
                return new BaseResponse(SystemManagerEnum.PASSWORD_INVALID.getCode(),SystemManagerEnum.PASSWORD_INVALID.getMsg());
            }
            return new BaseResponse(SystemManagerEnum.LOCKED.getCode(), SystemManagerEnum.LOCKED.getMsg());
        }
        //鉴权失败
        else if(e instanceof InsufficientAuthenticationException){
            return new BaseResponse(SystemManagerEnum.AUTH_ERROR.getCode(),SystemManagerEnum.AUTH_ERROR.getMsg());
        }
        return new BaseResponse(CommonEnum.SYSTEM_EXCEPTION.getCode(),CommonEnum.SYSTEM_EXCEPTION.getMsg());
    }
}
