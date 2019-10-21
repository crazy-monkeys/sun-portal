package com.crazy.portal;

import com.crazy.portal.bean.maintenance.MaintenanceBean;
import com.crazy.portal.service.ApiService;
import com.crazy.portal.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            Date date = DateUtil.parseDate("2019-09-09", DateUtil.WEB_FORMAT);
            String str = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        bean.setInstallDate("2019-09-09");
        bean.setInstallCec("test");
        bean.setContactName("test");
        bean.setContactEmial("test@qq.com");
        bean.setContactNumber("12323");
        bean.setContryName("中国");
        bean.setPostCode("123");
       try{
           //apiService.maintenaceApi(bean, new String[]{"0A2D8AA3DF254913ADBEF7CD54B86B4A", "A353A5739FFE417792A2AD350822FB44","630F73A8059C48C3A4435AAE4C4ABE6B"},"8FA7D41CD4C448BF9A27962E9055C141");
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
