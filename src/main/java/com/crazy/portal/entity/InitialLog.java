package com.crazy.portal.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 01:26 2019-11-21
 * @Modified by:
 */
@Data
@Entity
@ToString
public class InitialLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "business_key")
    private String businessKey;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer type;

    private Integer status;

    @Column(name = "error_msg",columnDefinition="TEXT")
    private String errorMsg;
}
