package com.crazy.portal.entity.system;

import java.util.Date;

/**
 * 
 * @author weiying
 * @date   2019-10-31 23:05::15
 */
public class SysSeq {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String seqModel;

    /**
     * 
     */
    private String seqDay;

    /**
     * 
     */
    private Integer seqValue;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTiem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeqModel() {
        return seqModel;
    }

    public void setSeqModel(String seqModel) {
        this.seqModel = seqModel == null ? null : seqModel.trim();
    }

    public String getSeqDay() {
        return seqDay;
    }

    public void setSeqDay(String seqDay) {
        this.seqDay = seqDay == null ? null : seqDay.trim();
    }

    public Integer getSeqValue() {
        return seqValue;
    }

    public void setSeqValue(Integer seqValue) {
        this.seqValue = seqValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTiem() {
        return updateTiem;
    }

    public void setUpdateTiem(Date updateTiem) {
        this.updateTiem = updateTiem;
    }
}