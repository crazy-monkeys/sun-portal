package com.crazy.portal.util;

import com.crazy.portal.config.exception.ErrorInfo;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 17:15 2019/6/8
 * @Modified by:
 */
public class ErrorCodes {

    public enum SystemEnum implements ErrorInfo {
        EXCEPTION(-1,"system.exception"),
        REQ_PARAM_FORMAT_ERROR(-2, "system.request.params.error"),
        SUCCESS(1,"system.success");

        private final int code;
        private final String key;

        SystemEnum(int code, String key) {
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
    public enum BusinessEnum implements ErrorInfo {
        TOKEN_IS_NULL(1001,"business.token.null"),
        PRODUCT_IS_EMPTY(1002,"business.product.empty"),
        SYS_PARAM_VALUE(1003,"business.param.value"),
        SYS_BEAN_ERROR(1004,"Object mapping exception"),
        PRODUCT_IS_PARALLEL_IMPORTS(1005,"business.product.parallel.imports"),
        IN_DATE_IS_BEFORE(1006,"business.in.date.is.before"),
        PRICE_IS_NULL(1007,"business.price.is.null"),
        INSTALL_DATE_IS_NOT_NULL(1008,"business.install.date.required"),
        EMAIL_IS_NO(1009,"business.email.error"),
        PRODUCT_BUSINESSPARTNER_IS_DIF(1010,"business.product.partner.diff"),
        ID_NON_MODEL(1011,"business.id.non.model"),
        EMAIL_IS_REQUIRED(1012,"business.email.required"),
        DELIVER_DATE_IS_REQUIRED(1013,"business.deliver.date.required"),
        NUMBER_NOT_DUBLE(1014,"business.number_not_duble"),
        EMAIL_IS_ERROR(1015,"business.email.format.error")
        ;



        private final int code;
        private final String msg;

        BusinessEnum(int code, String msg) {
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
