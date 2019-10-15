package com.crazy.portal.entity.maintenance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服务主信息
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
@Data
public class SunMaintenance {
    /**
     * 
     */
    private Integer id;

    /**
     * 单据类型 1-维保注册 2-服务请求 3-延保
     */
    private Integer type;

    /**
     * 所在国家
     */
    private String contry;

    /**
     * 安装人
     */
    private String installInstaller;

    /**
     * CEC认证号
     */
    private String installCec;

    /**
     * 总价格
     */
    private BigDecimal totalAmount;

    /**
     * 是否同意协议
     */
    private Integer isCheck;

    /**
     * 
     */
    private Date insertTime;

    /**
     * 建议
     */
    private String suggestions;
}