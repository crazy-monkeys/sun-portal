package com.crazy.portal.entity.maintenance;

import java.util.Date;

/**
 * 附件信息
 * @author weiying
 * @date   2019-10-15 01:24::58
 */
public class SunFiles {
    /**
     * 
     */
    private Integer fileId;

    /**
     * 
     */
    private Integer maintenanceId;

    /**
     * 附件类型 1-Invoice 2-Electrical Compliance Certificate 3-Support files
     */
    private String fileType;

    /**
     * 
     */
    private String fileSuffix;

    /**
     * 
     */
    private String fileName;

    /**
     * 
     */
    private String filePath;

    /**
     * 
     */
    private String fileUploadPath;

    /**
     * 
     */
    private Date insertTime;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Integer maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileUploadPath() {
        return fileUploadPath;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath == null ? null : fileUploadPath.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}