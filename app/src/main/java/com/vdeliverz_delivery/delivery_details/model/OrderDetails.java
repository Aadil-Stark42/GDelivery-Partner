
package com.vdeliverz_delivery.delivery_details.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDetails {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_referel")
    @Expose
    private String orderReferel;
    @SerializedName("earned")
    @Expose
    private String earned;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("item_total")
    @Expose
    private String itemTotal;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("pick_up")
    @Expose
    private String pickUp;
    @SerializedName("drop_off")
    @Expose
    private String dropOff;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("delivery_boy_type")
    @Expose
    private Boolean deliveryBoyType;

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

    public String getEarned() {
        return earned;
    }

    public void setEarned(String earned) {
        this.earned = earned;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getDeliveryBoyType() {
        return deliveryBoyType;
    }

    public void setDeliveryBoyType(Boolean deliveryBoyType) {
        this.deliveryBoyType = deliveryBoyType;
    }

}