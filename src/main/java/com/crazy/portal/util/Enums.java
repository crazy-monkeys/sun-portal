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

    //prod
    public enum COUNTRY_MAIL{
        AU("AU","service@sungrowpower.com.au","AUD"),
        CL("CL","latam.service@sa.sungrowpower.com",""),
        DE("DE","service.germany@sungrow-emea.com",""),
        FR("FR","service.france@sungrow.co",""),
        GR("GR ","service.greece@sungrow.co",""),
        ES("ES","service.spain@sungrow-emea.com",""),
        IN("IN","service@in.sungrowpower.com",""),
        IT("IT","service.italy@sungrow-emea.com",""),
        JP("JP","service@jp.sungrowpower.com",""),
        MX("MX","techsupport@sungrow-na.com",""),
        AE("AE","info@sungrow.cn",""),
        TH("TH","service@th.sungrowpower.com",""),
        BR("BR","latam.service@sa.sungrowpower.com",""),
        KR("Korea","service@kr.sungrowpower.com",""),
        PH("PH","service@ph.sungrowpower.com",""),
        CN("CN","info@sungrow.cn",""),
        GB("GB","service.uk@sungrow-emea.com",""),
        RO("RO","service.romania@sungrow.co",""),
        MY("MY","service@my.sungrowpower.com",""),
        ZA("ZA","info@sungrow.cn",""),
        TR("TR","service.turkey@sungrow.co",""),
        AC("US","techsupport@sungrow-na.com","USD"),
        VN("VN","service@vn.sungrowpower.com","");

        private String id;
        private String key;
        private String currency;

        public static String getKey(String id){
            for(COUNTRY_MAIL e : COUNTRY_MAIL.values()){
                if(id.equals(e.getId())){
                    return e.getKey();
                }
            }
            return null;
        }

        public static String getCurrency(String id){
            for(COUNTRY_MAIL e : COUNTRY_MAIL.values()){
                if(id.equals(e.getId())){
                    return e.getCurrency();
                }
            }
            return null;
        }

        COUNTRY_MAIL(String id, String key, String currency){
            this.id = id;
            this.key = key;
            this.currency = currency;
        }

        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public String getCurrency() {
            return currency;
        }
    }

    //Test
   /* public enum COUNTRY_MAIL{
        AU("AU","ranson.xie@acloudear.com","AUD"),
        CL("CL","ranson.xie@acloudear.com",""),
        DE("DE","ranson.xie@acloudear.com",""),
        FR("FR","ranson.xie@acloudear.com",""),
        GR("GR ","ranson.xie@acloudear.com",""),
        ES("ES","ranson.xie@acloudear.com",""),
        IN("IN","ranson.xie@acloudear.com",""),
        IT("IT","ranson.xie@acloudear.com",""),
        JP("JP","ranson.xie@acloudear.com",""),
        MX("MX","ranson.xie@acloudear.com",""),
        AE("AE","ranson.xie@acloudear.com",""),
        TH("TH","ranson.xie@acloudear.com",""),
        BR("BR","ranson.xie@acloudear.com",""),
        KR("Korea","ranson.xie@acloudear.com",""),
        PH("PH","ranson.xie@acloudear.com",""),
        CN("CN","ranson.xie@acloudear.com",""),
        GB("GB","ranson.xie@acloudear.com",""),
        RO("RO","ranson.xie@acloudear.com",""),
        MY("MY","ranson.xie@acloudear.com",""),
        ZA("ZA","ranson.xie@acloudear.com",""),
        TR("TR","ranson.xie@acloudear.com",""),
        AC("US","ranson.xie@acloudear.com","USD"),
        VN("VN","ranson.xie@acloudear.com","");

        private String id;
        private String key;
        private String currency;

        public static String getKey(String id){
            for(COUNTRY_MAIL e : COUNTRY_MAIL.values()){
                if(id.equals(e.getId())){
                    return e.getKey();
                }
            }
            return null;
        }

        public static String getCurrency(String id){
            for(COUNTRY_MAIL e : COUNTRY_MAIL.values()){
                if(id.equals(e.getId())){
                    return e.getCurrency();
                }
            }
            return null;
        }

        COUNTRY_MAIL(String id, String key, String currency){
            this.id = id;
            this.key = key;
            this.currency = currency;
        }

        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public String getCurrency() {
            return currency;
        }
    }*/

    //Prod
    public enum API_PARAMS{
        Installer("C80E939416524035B7BCABB3EBAE2832","installInstaller"), //安装人
        Installation_Date("6B79D51956514828891DA1C2FDCD6348","installDate"),  //安装时间
        CEC("9CC1B99FB8254E59BD5D6E3E979BB4AE","installCec"),  //cec
        ABN("6C903F8B8E784E8BB98CE745DAB0DEB2","abn"),   //abn
        Customer_Contact("9DF905DBA4544DFD9E49F1062C5590A2","customerContact"),  //维保公司名
        Contact_Email("A3051A8D1D914138A15CF5952A9D7C92","contactEmial"),   //邮件
        CC_Email("5BED898C95B24588B85AA64E701D4EA9","ccEmail"),   //邮件
        Contact_Number("4F081F58794A4A4299BF1B262D309B9F","contactNumber"), //number
        Customer_Address("BE5D7BBF824042129F5A1BF1C0DAB9F8","customerAddress"), //安装地址
        Business_Name("C1ED3124BC124EA89AA8C97E7867C49A","businessName"), //终端客户
        Reference("7554E75294FB4570AE94E20C5FD925C6","reference"),
        Electrical_Compliance_Certifica("06A122AAE3F6480FA3F35E45A8678ED5","electricalComplianceCertificate"),
        Invoice_Uploaded("DBEE85132F8D4ACBB44675D0F489548A","invliceUpload"),
        Fault_Type("706989EB09F74DD4B03C217A91F51A6F","faultType"),
        Fault_Description("9C8C8D03EA9C410DA133963B4A98CB7A","faultDescription"),
        Status_Message_LCD("945536B8653040FCA4AE6FD5FBFF8D86","lcdMessage"),
        Inverter_Exposed_Weather("6F3FE3BE645242EFA7C384DA15447F6B","isWeather"),
        Intervter_Connect_Battery("86ABBEA3C7FB4D1580503221EC34C8AA","battery"),
        Post_Code("23FD267E60094ED6A489DA5563E37054","postCode"),
        Country("4CE734A500224941A1AED3B0A1CA0C27","contryCode"),
        AMOUNT_EXCL_GST("C89114C2727440CB8712B0322E170FFE","amountGST"),
        GST("B05DB6A2147240708D7C294187F1C568","gst"),
        TOTAL_AMOUNT("B4C9C2D8AFB243D5B75EB5E769354A5A","totalAmount"),
        PURCHASE_ORDER("5B0AAF5D8AA44F4E96B7A2BF1C070EED","purchaseOrder"),
        SHIPPING_ADDRESS("A9A11FAAA7CF4791AB36F2FDEB39B89C","shippingAddress"),
        CURRENCY_CODE("FF58863588184D3597B14696FEB31381","currency"),
        INVERTER_INSTALLATION_LOCATION("6A0140DFE3C14AFD980A70F97C0E2EE2","location"),
        BATTERY_MODEL("EC377263EF144E2281ECBBC449DE598F","batteryModel"),

        Product_id("BE37C905B99A43CDA69DA687DDDAB061",""),
        Delivery_date("E23F3144040A4B689EEE76F53BA50F1E",""),
        Dispatched_Date("2E0CC222FA634535BB95FFFDBFD15C4F",""),
        accessory("6E2BFA66D0EF4BEEADCFDFE09CE53DEE","accessory"),

        Parts_Warranty_W5YP("E700C0A19A5C447FA749B788FB64B9A1","W5YP"),
        Parts_Warranty_W5YS("5C35915C193747DB91A87CE73C468586","W5YS"),
        ;

        private String id;
        private String key;

        public static String getId(String key){
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

    //Test
//    public enum API_PARAMS{
//        Installer("DC79C374404640FC80DCAC220F4B0015","installInstaller"), //安装人
//        Installation_Date("E63BC043F4E9493B8FC8FE4421D7D292","installDate"),  //安装时间
//        CEC("F92DF7E17DE246BE8EDF9DA23856E8C9","installCec"),  //cec
//        ABN("5268B5F3CF964C7EA9AEC91E869A89E4","abn"),   //abn
//        Customer_Contact("27D073A5FCFA4F6398D74B230595B078","customerContact"),  //维保公司名
//        Contact_Email("4AFC9CD0CD8C4AA7B9921B6EC4645B95","contactEmial"),   //邮件
//        CC_Email("2AEAA42EFDC34CC18C2E8BA2EC498550","ccEmail"),   //邮件
//        Contact_Number("EB7AF9DC6E8D49FC98DC67ACE96BFFB6","contactNumber"), //number
//        Customer_Address("9AD25F4EAEE54EAC86D6D7DD2E2FDA2E","customerAddress"), //安装地址
//        Business_Name("A20288A9EAD24CC28564B4EEA7B7968F","businessName"), //终端客户
//        Reference("0BB75D000E45404FAA3E2AB8189B1301","reference"),
//        Electrical_Compliance_Certificate("1029856393994F4695065FB1A76D00B9","electricalComplianceCertificate"),
//        Invoice_Uploaded("88D2D00FB3544918942470A7A3F6281C","invliceUpload"),
//        Fault_Type("CF85B3DC233E4B70852F409812713A37","faultType"),
//        Fault_Description("D31C9BE662904521A5A7577311D282FF","faultDescription"),
//        Status_Message_LCD("99763AADA25D422089AAAB338C49E848","lcdMessage"),
//        Inverter_Exposed_Weather("776769F766484874ADD9354E01AF0F16","isWeather"),
//        Intervter_Connect_Battery("B029CDE660DA4053BACC2322E90B29CD","battery"),
//        Post_Code("49D5913D6552439FABBCE76A26B57014","postCode"),
//        Country("48B4E9768FE14BC9B60D231B80893A36","contryCode"),
//        AMOUNT_EXCL_GST("DF5B2DC1E98F4509B11D866FFB2371C2","amountGST"),
//        GST("0A652A2381B94F5A8BA42C97970201EB","gst"),
//        TOTAL_AMOUNT("0CF10A5B6EA94732A8253B0C801E9249","totalAmount"),
//        PURCHASE_ORDER("248246FD02874D34B0CA85E8996C0DA4","purchaseOrder"),
//        SHIPPING_ADDRESS("042D3DDDD048464EA6B39E31527B926F","shippingAddress"),
//        CURRENCY_CODE("4A3E10C5776048BC984D45DA47026A37","currency"),
//        INVERTER_INSTALLATION_LOCATION("E6DB2E39349C4FEBB681500A674DF80D","location"),
//        BATTERY_MODEL("C65359DD9C8B49BFA12462263498CFF7","batteryModel"),
//
//        Product_id("8E6F00CE608B410AADCFBC378B83F4C2",""),
//        Delivery_date("5D66B7A7012843A49E9B2FF11F49C972",""),
//        Dispatched_Date("8073A0B54A9F492BABC49D02EDB48BA6",""),
//        accessory("528203E7CF6B4695BF9CD6CA6B9D0E14","accessory"),
//
//        Parts_Warranty_W5YP("A1B8254717004CF8A40E405B216352B5","W5YP"),
//        Parts_Warranty_W5YS("31FF6AE5353D4D498A7C68EF20454456","W5YS"),
//        ;
//
//        private String id;
//        private String key;
//
//        public static String getId(String key){
//            for(API_PARAMS e : API_PARAMS.values()){
//                if(key.equals(e.getKey())){
//                    return e.getId();
//                }
//            }
//            return null;
//        }
//
//        API_PARAMS(String id, String key){
//            this.id = id;
//            this.key = key;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public String getKey() {
//            return key;
//        }
//    }

    public enum API_HEADER_DTOS {
        EQUIPMENT20("Equipment.20"),
        SERVICECALL25("ServiceCall.25"),
        ACTIVITY28("Activity.28"),
        ADDRESS18("Address.18"),
        ITEM22("Item.22"),
        ACTIVITY("Activity.28"),
        MATERIAL("Material.19"),
        ATTACHMENT15("Attachment.15"),
        ITEMWAREHOUSELEVEL14("ItemWarehouseLevel.14"),
        WAREHOUSE15("Warehouse.15"),
        PERSON20("Person.20");

        API_HEADER_DTOS(String value){
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }

    public enum Sys_Seq{
        maintenance,servicecall,insurance
    }
}
