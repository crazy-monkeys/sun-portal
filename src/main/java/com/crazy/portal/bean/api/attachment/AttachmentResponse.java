package com.crazy.portal.bean.api.attachment;

import lombok.Data;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:39 2019-10-30
 * @Modified by:
 */
@Data
public class AttachmentResponse {

    private String fileName;
    private String description;
    private String owners;
    private String title;
    private String type;
    private String syncWithErp;
    private String syncObjectKPIs;
}
