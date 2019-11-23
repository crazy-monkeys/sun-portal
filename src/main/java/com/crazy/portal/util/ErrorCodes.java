package com.crazy.portal.util;

import com.crazy.portal.config.exception.ErrorInfo;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 17:15 2019/6/8
 * @Modified by:
 */
public class ErrorCodes {

    public enum CommonEnum implements ErrorInfo {
        SYSTEM_EXCEPTION(-1,"System exception"),
        SYSTEM_TIMEOUT(0,"System timeout"),
        SUCCESS(1,"system.success"),
        REQ_PARAM_FORMAT_ERROR(-2, "Request parameter format exception"),
        SERVER_MEETING(-4,"The server had a meeting"),
        REQ_ILLEGAL(-3, "Illegal request");


        private final int code;
        private final String key;

        CommonEnum(int code, String key) {
            this.code = code;
            this.key = key;
        }

        public int getCode() {
            return code;
        }

        public String getKey() {
            return key;
        }
    }

    /**
     * 系统管理状态码
     */
    public enum SystemManagerEnum implements ErrorInfo {
        TOKEN_IS_NULL(10048,"Token 获取失败"),
        PRODUCT_IS_EMPTY(10049,"Device information does not exist"),
        SYS_PARAM_VALUE(10048,"Code already exists under this option"),
        SYS_BEAN_ERROR(10049,"Object mapping exception"),
        PRODUCT_IS_PARALLEL_IMPORTS(10050,"The equipment is not in the maintenance service area"),
        IN_DATE_IS_BEFORE(10051,"Installation time cannot be later than creation time"),
        PRICE_IS_NULL(10052,"No corresponding price for equipment maintenance"),
        INSTALL_DATE_IS_NOT_NULL(10053,"Please select the installation time"),
        EMAIL_IS_NO(10054,"Confirm email error"),
        PRODUCT_BUSINESSPARTNER_IS_DIF(10055,"Equipment customer inconsistency"),
        ID_NON_MODEL(10056,"Please check the model for the current Serial number [%s] in the price list, you can not purchase warranty extension for the current model, please go to warranty  registration to register for it."),
        EMAIL_IS_NOT_NULL(10057,"Email is not Empty!"),
        deliver_date_is_empty(10058,"Local delivery time is empty!"),
        NUMBER_NOT_DUBLE(10059,"Device number cannot be repeated!"),
        EMAIL_IS_ERROR(10060,"Mailbox format error!")
        ;



        private final int code;
        private final String msg;

        SystemManagerEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }


        public int getCode() {
            return code;
        }

        @Override
        public String getKey() {
            return msg;
        }
    }
}
