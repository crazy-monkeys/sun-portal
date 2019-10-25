package com.crazy.portal.config.exception;

import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.util.ErrorCodes.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Created by lee on 2019/6/6.
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    /**
     * 全局异常捕捉
     * 注意:如果需要新加指定异常处理逻辑(x instanceOf x)需要在操作日志AOP中进行抛出,不然异常栈日志将会被aop进行全局捕获
     * @see com.crazy.portal.aop.OperationAspect setErrorMsgAndThrowException
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse parameterExceptionHandler(Exception exception) {

        if(exception instanceof MethodArgumentNotValidException){
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            String msg = super.getValidExceptionMsg(allErrors);
            return new BaseResponse(CommonEnum.REQ_PARAM_FORMAT_ERROR.getCode(),msg);
        }
        if(exception instanceof HttpMessageNotReadableException){
            return getMessageNotReadableErrorMessage(exception);
        }
        if(exception instanceof BindException){
            String msg = super.getValidExceptionMsg(((BindException) exception).getAllErrors());
            return new BaseResponse(CommonEnum.REQ_PARAM_FORMAT_ERROR.getCode(),msg);
        }
        if(exception instanceof BusinessException){
            BusinessException ex = (BusinessException)exception;
            return new BaseResponse(ex.getErrorCode(),ex.getMessage());
        }
        log.error("", exception);
        return new BaseResponse(CommonEnum.SYSTEM_EXCEPTION.getCode(),CommonEnum.SYSTEM_EXCEPTION.getMsg());
    }

    public BaseResponse getMessageNotReadableErrorMessage(Exception exception){
        String errorMessage = exception.getMessage().indexOf(":") != -1 ? exception.getMessage().substring(exception.getMessage().lastIndexOf(":") + 2, exception.getMessage().length()) : exception.getMessage();
        errorMessage = errorMessage.concat("格式错误");
        return new BaseResponse(CommonEnum.REQ_PARAM_FORMAT_ERROR.getCode(), errorMessage);
    }
}
