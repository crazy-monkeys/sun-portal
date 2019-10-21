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
        Installer("DC79C374404640FC80DCAC220F4B0015","installInstaller"),
        Installation_Date("E63BC043F4E9493B8FC8FE4421D7D292","installDate"),
        CEC("F92DF7E17DE246BE8EDF9DA23856E8C9","installCec"),
        ABN("5268B5F3CF964C7EA9AEC91E869A89E4",""),
        Customer_Contact("27D073A5FCFA4F6398D74B230595B078","contactName"),
        Contact_Email("4AFC9CD0CD8C4AA7B9921B6EC4645B95","contactEmial"),
        Contact_Number("EB7AF9DC6E8D49FC98DC67ACE96BFFB6","contactNumber"),
        Customer_Address("9AD25F4EAEE54EAC86D6D7DD2E2FDA2E",""),
        Business_Name("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Reference("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Electrical_Compliance_Certificate("A20288A9EAD24CC28564B4EEA7B7968F","electricalComplianceCertificate"),
        Invoice_Uploaded("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Fault_Type("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Fault_Description("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Status_Message_LCD("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Inverter_Exposed_Weather("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Intervter_Connect_Battery("A20288A9EAD24CC28564B4EEA7B7968F",""),
        Post_Code("49D5913D6552439FABBCE76A26B57014","postCode"),
        Country("49D5913D6552439FABBCE76A26B57014","contryName")
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
        ACTIVITY28("Activity.28");

        Api_Header_Dtos(String value){
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }
}
