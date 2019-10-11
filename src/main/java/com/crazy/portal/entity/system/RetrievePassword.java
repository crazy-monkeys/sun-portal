package com.crazy.portal.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author Bill
 * @date   2019-09-09 22:55::17
 */
@Data
public class RetrievePassword {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 随机数
     */
    private String randomCode;

    /**
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 1.生效 0失效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}