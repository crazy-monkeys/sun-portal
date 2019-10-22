package com.crazy.portal.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:44 2019/4/8
 * @Modified by:
 */
public class Enums {
    public enum YES_NO {
        YES(1),
        NO(0);

        private int code;

        YES_NO(Integer code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 用户类型
     * agent.代理商
     * subAgent.子代理商
     * internal.内部用户(展锐)
     */
    public enum USER_TYPE{
        agent,subAgent,internal
    }

    /**
     * 用户状态：正常、冻结
     */
    public enum USER_STATUS{
        normal(1),freeze(0);

        private final int code;

        USER_STATUS(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 资源类型: 菜单、按钮、接口api
     */
    public enum RESOURCE_TYPE_ENUM{
        MENU(1),BUTTON(2),API(3);

        private final int type;

        RESOURCE_TYPE_ENUM(int type){
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public static List<Integer> getResourceTypes(){
            List<Integer> types = new ArrayList<>();
            for(RESOURCE_TYPE_ENUM type : RESOURCE_TYPE_ENUM.values()){
                types.add(type.getType());
            }
            return types;
        }
    }

    public enum API_PARAMS{
        Installer("DC79C374404640FC80DCAC220F4B0015","installInstaller"), //安装人
        Installation_Date("E63BC043F4E9493B8FC8FE4421D7D292","installDate"),  //安装时间
        CEC("F92DF7E17DE246BE8EDF9DA23856E8C9","installCec"),  //cec
        ABN("5268B5F3CF964C7EA9AEC91E869A89E4","abn"),   //abn
        Customer_Contact("27D073A5FCFA4F6398D74B230595B078","contactName"),  //维保公司名
        Contact_Email("4AFC9CD0CD8C4AA7B9921B6EC4645B95","contactEmial"),   //邮件
        Contact_Number("EB7AF9DC6E8D49FC98DC67ACE96BFFB6","contactNumber"), //number
        Customer_Address("9AD25F4EAEE54EAC86D6D7DD2E2FDA2E","installAddress"), //安装地址
        Business_Name("A20288A9EAD24CC28564B4EEA7B7968F","contactBusinessName"), //终端客户
        Reference("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Electrical_Compliance_Certificate("A20288A9EAD24CC28564B4EEA7B7968F","electricalComplianceCertificate"),
        Invoice_Uploaded("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Fault_Type("A20288A9EAD24CC28564B4EEA7B7968F","warrantyType"),
        Fault_Description("A20288A9EAD24CC28564B4EEA7B7968F","faultDescription"),
        Status_Message_LCD("A20288A9EAD24CC28564B4EEA7B7968F","faultMassage"),
        Inverter_Exposed_Weather("A20288A9EAD24CC28564B4EEA7B7968F","isWeather"),
        Intervter_Connect_Battery("A20288A9EAD24CC28564B4EEA7B7968F","inverterConnect"),
        Post_Code("49D5913D6552439FABBCE76A26B57014","postCode"),
        Country("49D5913D6552439FABBCE76A26B57014","contryCode"),
        AMOUNT_EXCL_GST("BB18BEF42BB54039A1C51C4FDF72D2C5",""),
        GST("89BBA2CB6528442E84A3362DB109A419",""),
        TOTAL_AMOUNT("C36887DDD4E4486F958F0E80817DFB2F",""),
        PURCHASE_ORDER("248246FD02874D34B0CA85E8996C0DA4",""),

        Product_id("8E6F00CE608B410AADCFBC378B83F4C2",""),
        Delivery_date("",""),
        ;

        private String id;
        private String key;

        public String getId(String key){
            for(API_PARAMS e : API_PARAMS.values()){
                if(key.equals(e.getKey())){
                    return e.getId();
                }
            }
            return null;
        }

        API_PARAMS(String id, String key){
            this.id = id;
            this.key = key;
        }

        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }
    }

    public enum Api_Header_Dtos{
        EQUIPMENT20("Equipment.20"),
        SERVICECALL25("ServiceCall.25"),
        ACTIVITY28("Activity.28"),
        ADDRESS18("Address.18");

        Api_Header_Dtos(String value){
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }
}
