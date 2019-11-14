package com.crazy.portal.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:32 2019-09-15
 * @Modified by:
 */
@Data
@Entity
public class OperationLogDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String cookie;
    /**
     * 操作人暂时忽略
     */
    @Transient
    private String operator;

    /**
     * 访问url
     */
    private String url;
    /**
     * 调用参数
     */
    @Lob
    @Column(name = "invoke",columnDefinition="TEXT")
    private String invoke;
    /**
     * 业务查询key
     */
    @Column(name = "business_key")
    private String businessKey;
    /**
     * 异常消息
     */
    @Lob
    @Column(name = "error_msg",columnDefinition="TEXT")
    private String errorMsg;

    /**
     * 第三方请求url
     */
    @Lob
    @Column(name = "thirdparty_url",columnDefinition="TEXT")
    private String thirdpartyURL;

    /**
     * 第三方请求报文
     */
    @Lob
    @Column(name = "thirdparty_request",columnDefinition="TEXT")
    private String thirdpartyRequest;

    /**
     * 第三方返回报文
     */
    @Lob
    @Column(name = "thirdparty_response",columnDefinition="TEXT")
    private String thirdpartyResponse;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
