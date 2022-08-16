
package com.vdeliverz_delivery.home.model;


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
    @SerializedName("expected_time")
    @Expose
    private String expectedTime;
    @SerializedName("expected_date")
    @Expose
    private String expectedDate;

    @SerializedName("shop_name")
    @Expose
    private String shop_name;

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
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

}
