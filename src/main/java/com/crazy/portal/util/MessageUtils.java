package com.crazy.portal.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:35 2019-10-12
 * @Modified by:
 */
public class MessageUtils {

    private MessageUtils(){}

    public static MessageSource messageSource;

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }

    }
}
