
package com.vdeliverz_delivery.delivered.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveredListResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isDelivery_boy_type() {
        return delivery_boy_type;
    }

    public void setDelivery_boy_type(boolean delivery_boy_type) {
        this.delivery_boy_type = delivery_boy_type;
    }

    public  boolean delivery_boy_type;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public  String points;

}
