package com.crazy.portal.bean.api.attachment;

import com.crazy.portal.bean.api.ObjectBean;
import lombok.Data;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:43 2019-10-30
 * @Modified by:
 */
@Data
public class AttachmentRequest {

    private String fileName;
    private String fileContent;
    private ObjectBean object;
    private String title;
    private String type;

   /* public AttachmentRequest(){
        this.object = new JSONObject();
        object.put("objectType","SERVICECALL");
        object.put("objectId","5C4EEE7704914C69915E19D06F936B5A");
    }*/

   /* public JSONObject getObject() {
        return object;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }*/
}
