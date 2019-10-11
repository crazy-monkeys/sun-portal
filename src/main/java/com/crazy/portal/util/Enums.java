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

    public enum SYS_MODEL{
        FORECAST, //上传预测
        FORECAST_APPROVE; //上传、修改调整和最终审批
    }

    public enum SYS_PARAM{
        //附件类型
        ANNOUNCEMENT_TYPE(1,1),
        //客户业务类型
        CUSTOMER_BUSINESS_TYPE(2,1),
        //客户角色
        CUSTOMER_ROLE(2,2),
        //分级维护
        CUSTOMER_UNIT(2,3),
        CUSTOMER_PDT(2,4),
        //代理商关联cs 模块
        DEALER_MAPPING_MODEL(3,1),
        //出货期限  H
        DEALER_SALES_DATE(3,2),
        //收货期限 H
        DEALER_INVOICE_DATE(3,3),
        // 预测期限 D
        DEALER_FORECAST_DATE(3,4)
        ;

        private Integer model;
        private Integer function;
        SYS_PARAM(Integer model, Integer function){
            this.model = model;
            this.function = function;
        }

        public Integer getModel() {
            return model;
        }

        public Integer getFunction() {
            return function;
        }
    }

    /**
     * 审批状态
     * pending:待审批
     * pass:通过
     * reject:驳回
     */
    public enum APPROVAL_STATUS {
        pending,pass,reject;

        public static boolean isExists(String approvalStatus){
            for(Enum status : APPROVAL_STATUS.values()){
                if(String.valueOf(status).equals(approvalStatus)){
                    return true;
                }
            }
            return false;
        }
    }

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
     * 角色类型
     */
    public enum ROLE_TYPE {
        USER(1),
        SUB_USER(2);

        private int roleType;

        ROLE_TYPE(Integer roleType){
            this.roleType = roleType;
        }

        public int getRoleType() {
            return roleType;
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

    //<editor-fold desc="客户枚举">

    /**
     * A01 - Account Market（直供）；A02 - Account Market（非直供）；A03 - Mass Market；A04 - 代理商
     */
    public enum CUSTOMER_BUSINESS_TYPE{
        account_market_z("A01"),
        account_market("A02"),
        mass_market("A03"),
        dealer("A04");


        private final String code;
        CUSTOMER_BUSINESS_TYPE(String code){
            this.code = code;
        }
        public String getCode(){
            return code;
        }
    }

    public enum CUSTOMER_SHIP_TYPE{
        out_customer("Z001"),
        in_customer("Z002"),
        dealer("Z003");

        private String code;

        CUSTOMER_SHIP_TYPE(String code){
            this.code =code;
        }

        public String getCode() {
            return code;
        }
    }
    public enum CUSTOMER_TYPE{
        WAIT_SUBMIT(0, "潜在客户"),
        WAIT_REPORT(1,"未报备"),
        WAIT_APPROVAL(2, "已报备"),
        REPORT(3,"报备中"),
        OPEN(4,"OPEN 客户");

        private Integer code;
        private String desc;
        CUSTOMER_TYPE(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }
    /**
     *报备状态 0-初始化 1-可报备 2-审批中 3-审批通过 4-审批驳回
     */
    public enum CUSTOMER_APPROVE_STATUS{
        WAIT_SUBMIT(0, "初始化"),
        WAIT_REPORT(1,"可报备"),
        WAIT_APPROVAL(2, "审批中"),
        APPROVAL(3," 审批通过"),
        REJECT(4, "审批驳回"),

        REPORT(5,"已报备"),
        UNREPORT(6,"未报备"),;

        private Integer code;
        private String desc;
        CUSTOMER_APPROVE_STATUS(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }
    //</editor-fold>

    public enum RateType {
        /** 初始化 **/
        INITIALIZE(-1),
        /** 生效 **/
        ACTIVE(1),
        /** 失效 **/
        INACTIVE(0);

        private final int code;

        RateType(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum BusinessIdrStatus{
        APPROVAL_SUBMIT(1, "审批中"),
        AGREE(2, "审批通过"),
        FINISHED(3, "已完结"),
        REJECT(4, "已驳回");

        private Integer code;
        private String desc;

        BusinessIdrStatus(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }

    public enum BusinessFileType{
        COMMON(1, "普通附件"),
        IDR(2, "保差退附件"),
        FINANCIAL_CLOSURE(3, "财务完结附件");



        private Integer code;
        private String desc;

        BusinessFileType(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }

    public enum BusinessIdrReturnType{
        TH("退货"),
        HH("换货");

        private String desc;

        BusinessIdrReturnType(String desc){
            this.desc = desc;
        }
        public String getDesc(){
            return desc;
        }
    }

    public enum BusinessIdrApprovalSubmitType{
        //保价
        KP,
        //差价
        CP,
        //退货
        TH,
        //换货
        HH;
        BusinessIdrApprovalSubmitType(){
        }
    }

    public enum BusinessIdrApprovalSubmitResult{
        SUCCESS(1, "导入成功"),
        FAILED(0, "导入失败");

        private Integer code;
        private String desc;

        BusinessIdrApprovalSubmitResult(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }

    public enum BusinessIdrApprovalStatus{
        AGREE("Agree", "同意"),
        REJECT("Reject", "驳回");

        private String code;
        private String desc;

        BusinessIdrApprovalStatus(String code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public String getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }
    /** 保差退 end**/
    /** 客户rebate begin**/
    public enum BusinessRebateItemStatus{

        IN_APPROVAL(1, "结算中"),
        WAIT_CONFIRM(2, "客户待确认"),
        USED_CONFIRM(3, "客户已确认"),
        FINISHED(4, "执行完成");

        private Integer code;
        private String desc;

        BusinessRebateItemStatus(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }

    public enum BusinessRebateStatus{

        EXECUTE_PROCESS(1, "执行中"),
        FINISHED(2, "执行完成");

        private Integer code;
        private String desc;

        BusinessRebateStatus(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }
    }
    /** 客户rebate end**/
    public enum ProductStatus{
        ACTIVE(1),INACTIVE(0);
        private Integer status;
        ProductStatus(Integer status){
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }
    }

    public enum BI_FUNCTION_CODE{
        //交付管理 - 收货
        CHECK_INVENTORY_IMPORT_FILE,SAVE_INVENTORY_IMPORT_FILE,DELETE_INVENTORY_CASE,
        //交付管理 - 出货
        CHECK_SALES_IMPORT_FILE,SAVE_SALES_IMPORT_FILE,DELETE_SALES_CASE,UPDATE_SALES_IMPORT_FILE,
        //预测管理
        CHECK_FORECAST_IMPORT_DATA,INSERT_FORECAST_IMPORT_DATA,UPDATE_FORECAST_IMPORT_DAT,DELETEFORECAST;
    }

    /** 订单管理 **/
    public enum OrderType{
        //交货免费-不需要做价格模拟。-审批-纸质订单
        ZFD,
        //标准订单。-审批-纸质订单
        ZOR,
        //标准订单(ZRET)-必须录入ZRET订单号。-审批-纸质订单
        ZORT,
        //退货-需要找到原销售订单
        ZRET,
        //寄售 代理退货(RDA)-不需要关联原订单
        YKA,
        //客户库存补货(RDA)-寄售标准订单-审批-纸质订单
        YKB,
        //客户库存出货(RDA)-代理出给客户，传终端客户外部编号
        YKE,
        //客户库存补货
        KB,
        //客户库存出货
        KE,
        //标准客户库存出货
        ZKE,
        //标准客户库存补货
        ZKB;

        OrderType(){
        }
    }
    public enum OrderCustomerGroupOne{
        //客户专货订单
        A01,
        //Buffer订单
        A02,
        //新产品订单
        A03,
        //样品订单
        A04,
        //Last Buy订单
        A05,
        //分销商专货订单
        A06;
        OrderCustomerGroupOne(){
        }
    }
    public enum CustomerType {
        B1("Account Market"),
        B2("Mass Market");
        private String desc;
        CustomerType(String desc){
            this.desc = desc;
        }
        public String getDesc() {
            return desc;
        }
        public static String getDescByCode(String code){
            for(CustomerType e : CustomerType.values()){
                if(e.toString().equals(code)){
                    return e.getDesc();
                }
            }
            return null;
        }
    }
    public enum OrderMaterialNumber{
        /** 虚拟料号 **/
        TAP,
        /** 实体料 **/
        ZTAN;
        OrderMaterialNumber(){
        }
    }
    public enum OrderApprovalStatus{
        WAIT_APPROVAL(0, "待审批"),
        ADOPT(1, "已通过"),
        REJEC(2, "已驳回");

        private Integer value;
        private String desc;
        OrderApprovalStatus(Integer value, String desc){
            this.value = value;
            this.desc = desc;
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum OrderApprovalType{
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        CANCEL("CANCEL"),;

        private String value;
        OrderApprovalType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CustomerMappingModel{
        Forecast("forecast"),
        HH("换货");

        private String value;

        CustomerMappingModel(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
    }
}
