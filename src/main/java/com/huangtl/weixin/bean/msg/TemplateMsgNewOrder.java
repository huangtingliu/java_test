package com.huangtl.weixin.bean.msg;

import java.util.Date;

/**
 * 新订单模板消息体
 */
public class TemplateMsgNewOrder {

    private String orderId;
    /**客户*/
    private String userName;
    /**服务名称*/
    private String serviceName;
    /**预约时间*/
    private Date serviceTime;
    /**联系电话*/
    private String phone;
    /**地址*/
    private String address;

    public TemplateMsgNewOrder() {
    }

    public TemplateMsgNewOrder(String orderId,String userName, String serviceName, Date serviceTime, String phone, String address) {
        this.userName = userName;
        this.serviceName = serviceName;
        this.serviceTime = serviceTime;
        this.phone = phone;
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
