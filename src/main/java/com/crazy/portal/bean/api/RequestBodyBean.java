package com.crazy.portal.bean.api;

import com.crazy.portal.bean.api.device.UdfValuesBean;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: RequestBodyBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/16 00:55
 */
@Data
public class RequestBodyBean {
    //客户
    private String businessPartner;
    //主题
    private String subject="Warranty Registration";
    //优先级(MEDIUM)
    private String priority="MEDIUM";
    //同步状态（固定值：IN_CLOUD）
    private String syncStatus="IN_CLOUD";
    //类型（0003）
    private String typeCode="0003";
    //类型名称（Warranty Registration）
    private String typeName="Warranty Registration";
    //状态码（-5/Z01/-2/-1/-4/-3）
    private String statusCode="-3";
    //状态名称(Technically Complete）
    private String statusName="Technically Complete";
    //来源码（-3）
    private String originCode="-3";
    //来源名称(Web Portal)
    private String originName="Web Portal";
    //问题类型码（）
    private String problemTypeCode;
    //	问题类型名称()
    private String problemTypeName;
    //开始
    private Date startDateTime=new Date();
    //结束
    private Date endDateTime;
    //到期
    private Date dueDateTime;
    //负责人
    //private String responsibles;
    //联系人
    private String contact;
    //设备
    private String[] equipments;
    //udt
    private List<UdfValuesBean> udfValues;
}
