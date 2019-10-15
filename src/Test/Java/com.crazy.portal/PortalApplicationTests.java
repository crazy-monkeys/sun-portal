package com.crazy.portal;

import com.crazy.portal.service.ApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName: PortalApplicationTests
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 23:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PortalApplicationTests {
    @Resource
    private ApiService apiService;

    @Test
    public void test(){
        apiService.getToken();
    }
}
