
package com.vdeliverz_delivery.ongoing_orders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_referel")
    @Expose
    private String orderReferel;
    @SerializedName("total_items")
    @Expose
    private String totalItems;
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("pick_up")
    @Expose
    private String pickUp;
    @SerializedName("drop_off")
    @Expose
    private String dropOff;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("expected_time")
    @Expose
    private String expectedTime;
    @SerializedName("expected_date")
    @Expose
    private String expectedDate;
    @SerializedName("cancel_time_min")
    @Expose
    private String cancelTimeMin;
    @SerializedName("cancel_time_sec")
    @Expose
    private String cancelTimeSec;
    @SerializedName("can_cancel")
    @Expose
    private Boolean canCancel;

    @SerializedName("customer_mobile")
    @Expose
    private String customer_mobile;

    @SerializedName("shop_lat")
    @Expose
    private String shop_lat;

    @SerializedName("shop_lon")
    @Expose
    private String shop_lon;

    @SerializedName("cus_lat")
    @Expose
    private String cus_lat;

    @SerializedName("cus_lon")
    @Expose
    private String cus_lon;

    @SerializedName("shop_name")
    @Expose
    private String shop_name;

    @SerializedName("shop_mobile")
    @Expose
    private String shop_mobile;
    @SerializedName("cus_name")
    @Expose
    private String cus_name;

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_mobile() {
        return shop_mobile;
    }

    public void setShop_mobile(String shop_mobile) {
        this.shop_mobile = shop_mobile;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_lat() {
        return shop_lat;
    }

    public void setShop_lat(String shop_lat) {
        this.shop_lat = shop_lat;
    }

    public String getShop_lon() {
        return shop_lon;
    }

    public void setShop_lon(String shop_lon) {
        this.shop_lon = shop_lon;
    }

    public String getCus_lat() {
        return cus_lat;
    }

    public void setCus_lat(String cus_lat) {
        this.cus_lat = cus_lat;
    }

    public String getCus_lon() {
        return cus_lon;
    }

    public void setCus_lon(String cus_lon) {
        this.cus_lon = cus_lon;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderReferel() {
        return orderReferel;
    }

    public void setOrderReferel(String orderReferel) {
        this.orderReferel = orderReferel;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDropOff() {
        return dropOff;
    }

    public void setDropOff(String dropOff) {
        this.dropOff = dropOff;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getCancelTimeMin() {
        return cancelTimeMin;
    }

    public void setCancelTimeMin(String cancelTimeMin) {
        this.cancelTimeMin = cancelTimeMin;
    }

    public String getCancelTimeSec() {
        return cancelTimeSec;
    }

    public void setCancelTimeSec(String cancelTimeSec) {
        this.cancelTimeSec = cancelTimeSec;
    }

    public Boolean getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(Boolean canCancel) {
        this.canCancel = canCancel;
    }

}
