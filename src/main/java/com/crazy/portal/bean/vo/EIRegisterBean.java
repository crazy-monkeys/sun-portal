package com.crazy.portal.bean.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @ClassName: EIRegisterBean
 * @Author: God Man Qiu~
 * @Date: 2019/10/28 14:13
 */
@Data
public class EIRegisterBean {
    private String type;
    private String billType;
    private String businessName;
    private String abn;
    private String postCode;
    private AddressBean address;

    private String firstName;
    private String lastName;
    @Email(message="邮箱格式错误")
    private String email;
    @Email(message="邮箱格式错误")
    private String sendEmail;
    private String contactNumber;
    private List<ProductBean> products;
    private String installDate;
    private String shippingAddress;
    private String purchaseOrder;
}
