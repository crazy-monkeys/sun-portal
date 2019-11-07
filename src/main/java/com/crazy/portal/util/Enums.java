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
        Customer_Contact("27D073A5FCFA4F6398D74B230595B078","customerContact"),  //维保公司名
        Contact_Email("4AFC9CD0CD8C4AA7B9921B6EC4645B95","contactEmial"),   //邮件
        Contact_Number("EB7AF9DC6E8D49FC98DC67ACE96BFFB6","contactNumber"), //number
        Customer_Address("9AD25F4EAEE54EAC86D6D7DD2E2FDA2E","customerAddress"), //安装地址
        Business_Name("A20288A9EAD24CC28564B4EEA7B7968F","businessName"), //终端客户
        Reference("0BB75D000E45404FAA3E2AB8189B1301","reference"),
        Electrical_Compliance_Certificate("1029856393994F4695065FB1A76D00B9","electricalComplianceCertificate"),
        Invoice_Uploaded("88D2D00FB3544918942470A7A3F6281C","invliceUpload"),
        Fault_Type("CF85B3DC233E4B70852F409812713A37","faultType"),
        Fault_Description("D31C9BE662904521A5A7577311D282FF","faultDescription"),
        Status_Message_LCD("99763AADA25D422089AAAB338C49E848","lcdMessage"),
        Inverter_Exposed_Weather("776769F766484874ADD9354E01AF0F16","isWeather"),
        Intervter_Connect_Battery("B029CDE660DA4053BACC2322E90B29CD","battery"),
        Post_Code("49D5913D6552439FABBCE76A26B57014","postCode"),
        Country("48B4E9768FE14BC9B60D231B80893A36","contryCode"),
        AMOUNT_EXCL_GST("DF5B2DC1E98F4509B11D866FFB2371C2","amountGST"),
        GST("0A652A2381B94F5A8BA42C97970201EB","gst"),
        TOTAL_AMOUNT("0CF10A5B6EA94732A8253B0C801E9249","totalAmount"),
        PURCHASE_ORDER("248246FD02874D34B0CA85E8996C0DA4","purchaseOrder"),
        SHIPPING_ADDRESS("042D3DDDD048464EA6B39E31527B926F","shippingAddress"),
        CURRENCY_CODE("4A3E10C5776048BC984D45DA47026A37","currency"),
        INVERTER_INSTALLATION_LOCATION("E6DB2E39349C4FEBB681500A674DF80D","location"),
        BATTERY_MODEL("C65359DD9C8B49BFA12462263498CFF7","batteryModel"),

        Product_id("8E6F00CE608B410AADCFBC378B83F4C2",""),
        Delivery_date("5D66B7A7012843A49E9B2FF11F49C972",""),

        Parts_Warranty_W5YP("A1B8254717004CF8A40E405B216352B5","W5YP"),
        Parts_Warranty_W5YS("31FF6AE5353D4D498A7C68EF20454456","W5YS"),
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
