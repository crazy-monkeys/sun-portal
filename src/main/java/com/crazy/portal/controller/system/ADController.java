package com.crazy.portal.controller.system;

import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.config.security.JwtUserService;
import com.crazy.portal.config.security.handler.LoginSuccessHandler;
import com.crazy.portal.controller.BaseController;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//@RestController
@Slf4j
//@RequestMapping("/ad")
public class ADController extends BaseController {

    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private JwtUserService jwtUserService;

    /**
     * Kerberos  / Spnego Negotiation 登录成功之后获取资源列表
     * @see com.crazy.portal.config.security.config.SecurityConfig KerberosServiceAuthenticationProvider
     * @param response
     * @return
     */
    @GetMapping("/index")
    public BaseResponse index(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //用户kerberos认证成功之后，再次点击，应该返回用户当前权限
        Map<String,?> userPermissions = loginSuccessHandler.getUserPermissions();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader(Constant.Authorization, jwtUserService.generateToken(userDetails));
        return super.successResult(userPermissions);
    }

    @GetMapping("/forward")
    public void authTicket(HttpServletResponse response){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");
        BusinessUtil.assertTrue(false, ErrorCodes.SystemManagerEnum.ACCOUNT_ERROR);
    }
}
