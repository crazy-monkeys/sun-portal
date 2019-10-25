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
    public void createTest(){
        MaintenanceBean bean = new MaintenanceBean();
        bean.setInstallInstaller("Test");

            bean.setInstallDate("2019-09-09");

        bean.setInstallCec("test");
        bean.setContactName("test");
        bean.setContactEmial("test@qq.com");
        bean.setContactNumber("12323");
        bean.setContryName("中国");
        bean.setPostCode("123");

        bean.setBusinessPartner("8FA7D41CD4C448BF9A27962E9055C141");
       try{
           apiService.maintenaceApi(bean);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
