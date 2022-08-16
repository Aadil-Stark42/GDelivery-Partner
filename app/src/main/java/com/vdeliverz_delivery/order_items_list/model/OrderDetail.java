package com.vdeliverz_delivery.order_items_list.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("shop_details")
    @Expose
    private ShopDetails shopDetails;
    @SerializedName("product_details")
    @Expose
    private List<ProductDetail> productDetails = null;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("is_scheduled")
    @Expose
    private String isScheduled;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ShopDetails getShopDetails() {
        return shopDetails;
    }

    public void setShopDetails(ShopDetails shopDetails) {
        this.shopDetails = shopDetails;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(String isScheduled) {
        this.isScheduled = isScheduled;
    }

}
