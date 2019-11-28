package com.crazy.portal.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:46 2019-11-28
 * @Modified by:
 */
@RestController
@RequestMapping("/i18n")
public class I18nController {

    /**
     * 切换入口，由拦截器实现
     * @param lang
     * @return
     */
    @GetMapping("/change")
    public String changeI18n(@RequestParam String lang){
        return lang;
    }
}
