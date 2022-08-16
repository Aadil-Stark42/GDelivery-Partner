
package com.vdeliverz_delivery.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("notify_id")
    @Expose
    private Integer notifyId;
    @SerializedName("notify_head")
    @Expose
    private String notifyHead;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("time")
    @Expose
    private String time;

    public Integer getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Integer notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyHead() {
        return notifyHead;
    }

    public void setNotifyHead(String notifyHead) {
        this.notifyHead = notifyHead;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
