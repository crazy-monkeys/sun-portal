package com.crazy.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:53 2019-11-14
 * @Modified by:
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(){

        return "redirect:https://sunportal-fb3323b80.dispatcher.cn1.hana.ondemand.com/sunportal/index.html";
    }
}
