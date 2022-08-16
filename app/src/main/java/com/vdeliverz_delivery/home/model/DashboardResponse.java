
package com.vdeliverz_delivery.home.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DashboardResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("terms_and_conditions")
    @Expose
    private String terms_and_conditions;
    @SerializedName("privacy_policy")
    @Expose
    private String privacy_policy;
    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    public String getTerms_and_conditions() {
        return terms_and_conditions;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
