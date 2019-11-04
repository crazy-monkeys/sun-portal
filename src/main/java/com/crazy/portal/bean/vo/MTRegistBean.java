package com.crazy.portal.bean.vo;

import lombok.Data;

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

}
