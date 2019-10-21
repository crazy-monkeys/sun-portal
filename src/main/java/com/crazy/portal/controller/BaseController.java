package com.crazy.portal.controller;

import com.crazy.portal.bean.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 12:52 2019/5/3
 * @Modified by:t
 */
@Component
@Slf4j
public class BaseController {

    /**
     * 获取参数校验失败信息
     * @param errorList
     * @return
     */
    public String getValidExceptionMsg(List<ObjectError> errorList) {
        return errorList.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    protected BaseResponse successResult() {
        BaseResponse response = new BaseResponse();
        response.success();
        return response;
    }

    protected BaseResponse successResult(Object data) {
        BaseResponse response = new BaseResponse();
        response.success(data);
        return response;
    }
}
