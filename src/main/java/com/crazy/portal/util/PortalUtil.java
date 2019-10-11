package com.crazy.portal.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * Created by lee on 2019/6/5.
 */
public class PortalUtil {

    private static int DEFAULT_PAGE_NUM = 1;
    private static int DEFAULT_PAGE_SIZE = 10;

    /**
     * 开启分页查询
     * 默认页码：1
     * 默认每页显示数量：10
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @return
     */
    public static Page defaultStartPage(Integer pageNum, Integer pageSize) {
        return PageHelper.startPage(null == pageNum ? DEFAULT_PAGE_NUM : pageNum, null == pageSize ? DEFAULT_PAGE_SIZE : pageSize);
    }

    /**
     * 重置密码随机生成10位密码数
     * @return
     */
    public static String generateRandomPassword() {
        int length = 10;
        char[] ss = new char[10];
        int[] flag = { 0, 0, 0 }; // A-Z, a-z, 0-9
        int i = 0;
        while (flag[0] == 0 || flag[1] == 0 || flag[2] == 0 || i < length) {
            i = i % length;
            int f = (int) (Math.random() * 3 % 3);
            if (f == 0)
                ss[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                ss[i] = (char) ('a' + Math.random() * 26);
            else
                ss[i] = (char) ('0' + Math.random() * 10);
            flag[f] = 1;
            i++;
        }
        return new String(ss) + "$";
    }

}
