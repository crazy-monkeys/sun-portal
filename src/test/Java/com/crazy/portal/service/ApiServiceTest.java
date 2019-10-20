package com.crazy.portal.service;

import com.crazy.portal.bean.api.token.TokenBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 00:10 2019-10-21
 * @Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiServiceTest {

    @Resource
    private ApiService apiService;

    @Test
    public void getHeader() {
        TokenBean tokenBean = apiService.getToken();
        Map<String, String> header = apiService.getHeader(tokenBean);

        log.info(header.toString());
        Assert.assertTrue(header.get("x-client-id").equals("123d01ed-e117-4ade-afc4-7e697aa4594f"));
    }
}