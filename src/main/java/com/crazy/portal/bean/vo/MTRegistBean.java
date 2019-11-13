package com.crazy.portal.bean.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: MTRegistBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/28 11:18
 */
@Data
public class MTRegistBean {
    private String type;
    private String country;
    @Valid
    private ContactBean contacts;
    private AddressBean address;

    private List<ProductBean> products;
    private String installInstaller;
    private String installDate;
    private String installCec;
    private String suggestions;
    private String businessPartner;

    @JSONField(serialize = false)
    private MultipartFile invoiceFile;
    @JSONField(serialize = false)
    private MultipartFile cecFile;
}
