
package com.vdeliverz_delivery.search_delivered.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.vdeliverz_delivery.delivered.model.Order;

public class DeliveredSearchResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("delivery_boy_type")
    @Expose
    private Boolean deliveryBoyType;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getDeliveryBoyType() {
        return deliveryBoyType;
    }

    public void setDeliveryBoyType(Boolean deliveryBoyType) {
        this.deliveryBoyType = deliveryBoyType;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
