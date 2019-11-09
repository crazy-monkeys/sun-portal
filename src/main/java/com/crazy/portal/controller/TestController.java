package com.crazy.portal.controller;

import com.crazy.portal.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:42 2019-11-08
 * @Modified by:
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test/count")
    public long count() {
        log.info("Search total number of employees");
        return testService.getCount();
    }

    @GetMapping(value = "/test/add")
    public boolean addEmployee() {
        return testService.insertTest();
    }
}
