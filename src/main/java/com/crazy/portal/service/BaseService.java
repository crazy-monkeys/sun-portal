package com.crazy.portal.service;

import com.alibaba.fastjson.JSONObject;
import com.crazy.portal.bean.api.ParamsBean;
import com.crazy.portal.bean.api.token.TokenBean;
import com.crazy.portal.bean.common.Constant;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.entity.OperationLogDO;
import com.crazy.portal.repository.OperationLogRepository;
import com.crazy.portal.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:08 2019-10-21
 * @Modified by:
 */
@Component
@Slf4j
public class BaseService {

    @Value("${coresuite.api.token-root-url}")
    protected String tokenRootUrl;

    @Value("${coresuite.api.call-root-url}")
    protected String callRootUrl;

    @Value("${coresuite.authorization}")
    protected String authorization;

    @Value("${coresuite.grant_type}")
    protected String grantType;

    @Value("${coresuite.username}")
    protected String username;

    @Value("${coresuite.password}")
    protected String password;

    @Value("${coresuite.mimeType}")
    protected String mimeType;

    @Resource
    private OperationLogRepository operationLogRepository;

    /**
     * 获取token
     * @return
     */
    protected TokenBean getToken(){
        try{
            String url = String.format("%s%s", tokenRootUrl,"/oauth2/v1/token");
            String params = String.format("grant_type=%s&username=%s&password=%s", grantType, username, password);
            Map<String, String> header = new HashMap<>();
            header.put(Constant.Authorization, authorization);
            String response = HttpClientUtils.post(url, params, mimeType, header);
            BusinessUtil.notNull(response, ErrorCodes.SystemManagerEnum.TOKEN_IS_NULL);
            return JSONObject.parseObject(response, TokenBean.class);
        }catch (Exception e){
            log.error("接口异常:"+e);
            throw new BusinessException("token 获取失败",e);
        }
    }

    /**
     * 封装通用调用api
     * @param url
     * @param body
     * @param dtos
     * @return
     * @throws Exception
     */
    protected String invokeApi(String url, String body, Enums.API_HEADER_DTOS dtos){
        return this.getApiResponse(url, body, dtos,null);
    }

    /**
     * 封装通用调用api
     * @param url
     * @param body
     * @param dtos
     * @return
     * @throws Exception
     */
    protected String invokeApi(String url, String body, Enums.API_HEADER_DTOS dtos, Boolean forceUpdate){
        return this.getApiResponse(url, body, dtos,forceUpdate);
    }

    private String getApiResponse(String url, String body, Enums.API_HEADER_DTOS dtos, Boolean forceUpdate){
        String response = null;
        String errorMsg = null;
        String requestUUID;
        String buildFinalUrl = null;
        OperationLogDO operationLogDO = null;
        try {
            requestUUID = this.getRequestUUID();
            if(Objects.nonNull(requestUUID)){
                operationLogDO = operationLogRepository.findByCookie(requestUUID);
            }
            TokenBean tokenBean = this.getToken();
            Map<String,String> header = this.buildHeader(tokenBean);
            String account = tokenBean.getAccount();
            String company = tokenBean.getCompanies().get(0).getName();
            String user = tokenBean.getUser();
            ParamsBean ParamsBean = new ParamsBean(account, company, user, header, dtos.getValue(),forceUpdate);
            buildFinalUrl = String.format("%s?%s",url,ParamsBean.toString());

            if(forceUpdate != null){
                response = HttpClientUtils.patch(buildFinalUrl, body, "application/json", header);
            }else{
                response = HttpClientUtils.post(buildFinalUrl, body, "application/json", header);
            }
            if(response.isEmpty()){
                throw new RuntimeException("error invoke");
            }
        } catch (Exception e) {
            log.error("",e);
            errorMsg = ExceptionUtils.getExceptionAllinformation(e);
        } finally {
            //更新操作日志记录
            if(Objects.nonNull(operationLogDO)){
                String thirdpartyURL = operationLogDO.getThirdpartyURL();
                StringBuilder sbRrl = new StringBuilder(thirdpartyURL == null ? "" : thirdpartyURL);
                operationLogDO.setThirdpartyURL(sbRrl.toString() + "\r\n" + buildFinalUrl);

                String thirdpartyRequest = operationLogDO.getThirdpartyRequest();
                StringBuilder sbReq = new StringBuilder(thirdpartyRequest == null ? "" : thirdpartyRequest);
                operationLogDO.setThirdpartyRequest(sbReq.toString()+"\r\n" + body);

                String thirdpartyResponse = operationLogDO.getThirdpartyResponse();
                StringBuilder sbRes = new StringBuilder(thirdpartyResponse == null ? "" : thirdpartyResponse);
                operationLogDO.setThirdpartyResponse(sbRes.toString() + "\r\n" + response);

                String dbErrorMsg = operationLogDO.getErrorMsg();
                StringBuilder sbErrorMsg = new StringBuilder(dbErrorMsg == null ? "" : dbErrorMsg);
                operationLogDO.setErrorMsg(sbErrorMsg.toString() + "\r\n" + errorMsg);

                operationLogRepository.save(operationLogDO);
            }
        }
        return response;
    }

    public static void main(String[] args) {
        System.out.println("1" + "\r\n" + "2");
    }

    /**
     * 构建接口调用头信息
     * @param tokenBean
     * @return
     */
    private Map<String,String> buildHeader(TokenBean tokenBean){
        Map<String,String> header = new HashMap<>();
        header.put(Constant.Authorization,String.format("%s %s",tokenBean.getToken_type(),tokenBean.getAccess_token()));
        header.put("Accept",MediaType.APPLICATION_JSON_VALUE);
        header.put("Cache-Control","no-cache");

        Pattern pattern = Pattern.compile("Basic (.*)");
        Matcher m = pattern.matcher(authorization);
        if(m.find()){
            String secret = new String(Base64Utils.decode(m.group(1).getBytes()));
            header.put("x-client-id", secret.split(":")[0]);
        }
        header.put("x-client-version", "1.0");
        header.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return header;
    }

    private HttpServletRequest getRequest(){
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes,"requestAttributes is null");
            return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        } catch (Exception e) {
            log.error("",e);
            return null;
        }
    }

    protected String getRequestUUID(){
        HttpServletRequest request = this.getRequest();
        if(request == null){
            return null;
        }
        Object uuid = request.getAttribute("UUID");
        return uuid == null ? null : uuid.toString();
    }
}
