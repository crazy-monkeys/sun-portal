package com.crazy.portal.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:35 2019-10-12
 * @Modified by:
 */
public class I18NUtils {

    private I18NUtils(){}

    public static MessageSource messageSource;


    public static String getMsg(String key) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return key;
        }
    }

    public static String getMsg(String key, Object[] args) {
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return key;
        }
    }
}
