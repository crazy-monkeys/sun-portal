package com.crazy.portal.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.config.security.JwtAuthenticationToken;
import com.crazy.portal.service.system.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 资源访问过滤器
 * 1.token认证
 * 2.url鉴权
 * @Author: Bill
 * @Date: created in 20:03 2019/4/20
 * @Modified by:
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private List<RequestMatcher> permissiveRequestMatchers;
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private AntPathMatcher antPathMatcher;


    public JwtAuthenticationFilter() {
        this.successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        this.failureHandler = new SimpleUrlAuthenticationFailureHandler();
        this.antPathMatcher = new AntPathMatcher();
    }

    @Value("${app.ad-domain}")
    private String adDomain;

    @Resource
    PermissionService permissionService;

    private static final String AD_LOGIN_URL = "/ad/index";

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
        Assert.notNull(successHandler, "AuthenticationSuccessHandler must be specified");
        Assert.notNull(failureHandler, "AuthenticationFailureHandler must be specified");
    }

    /**
     * 资源访问过滤器
     * 1.token认证
     * 2.url鉴权
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            //可以忽略Token权限的url
            if (this.canIgnorePermiss(request, response, filterChain)){
                return;
            }

            String token = this.getToken(request);
            if(StringUtils.isEmpty(token)){
                this.authenticationFailure(request, response,
                        new InsufficientAuthenticationException("Url does not carry tokens"));
                return;
            }
            //根据token获取身份认证
            Authentication authResult = this.getAuthentication(token);
            //token认证不成功
            if(authResult == null){
                this.authenticationFailure(request, response,
                        new InsufficientAuthenticationException("Token authentication failed"));
                return;
            }

            UserDetails userDetails = (UserDetails) authResult.getPrincipal();
            if(!authRequest(request,userDetails.getUsername())){
                //鉴权失败处理
                this.authenticationFailure(request, response,
                        new InsufficientAuthenticationException("Insufficient permissions"));

                return;
            }
            //认证成功并放行
            this.successfulAuthentication(request, response, authResult);
            filterChain.doFilter(request, response);
            return;
        }catch (LockedException e){
            this.authenticationFailure(request, response,e);
        }catch(JWTDecodeException | NonceExpiredException | BadCredentialsException e) {
            log.error("",e);
            this.authenticationFailure(request, response,new InsufficientAuthenticationException("Authentication failed", e));
        }
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    protected String getToken(HttpServletRequest request) {
        String authInfo = request.getHeader(Constant.Authorization);
        return StringUtils.removeStart(authInfo, "Bearer ");
    }

    /**
     * 检查请求的url 是否具备访问权限
     * @param request
     * @return
     */
    protected boolean authRequest(HttpServletRequest request,String userName) {
        try {
            //用户在系统的权限
            List<com.crazy.portal.entity.system.Resource> resources = permissionService.findAllPerMissionByUserId(userName);
            String requestUrl = request.getServletPath();
            for(com.crazy.portal.entity.system.Resource resource : resources){
                if(antPathMatcher.match(resource.getPermissionPrefixUrl(),requestUrl)){
                    return true;
                }
            }
            logger.error(String.format("Insufficient access rights for %s user to %s",userName,request.getRequestURI()));
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Url authentication failed",e);
        }
    }

    /**
     * 检查请求的url是否可以匿名访问
     * @param request
     * @param response
     * @param filterChain
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private boolean canIgnorePermiss(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(permissiveRequestMatchers != null && !permissiveRequestMatchers.isEmpty()){
            for(RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
                if(permissiveMatcher.matches(request)){
                    //匹配则放行
                    filterChain.doFilter(request, response);
                    return true;
                }
            }
        }
        String requestUrl = request.getServletPath();
        //对ad登录的链接设置不校验token
        if(requestUrl.contains(AD_LOGIN_URL)){
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    /**
     * 通过token获取身份验证器
     * @param token
     * @return
     */
    private Authentication getAuthentication(String token){
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(JWT.decode(token));
        Authentication authResult = this.getAuthenticationManager().authenticate(authToken);
        return authResult;
    }


    protected void authenticationFailure(HttpServletRequest request,
                                         HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response,
                                            Authentication authResult) throws IOException, ServletException{

        SecurityContextHolder.getContext().setAuthentication(authResult);
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }



    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {

        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {

        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    public void setPermissiveUrl(String... urls) {
        if(permissiveRequestMatchers == null){
            permissiveRequestMatchers = new ArrayList<>();
        }
        Stream.of(urls).forEach(x->
                permissiveRequestMatchers.add(new AntPathRequestMatcher(x))
        );
    }
}
