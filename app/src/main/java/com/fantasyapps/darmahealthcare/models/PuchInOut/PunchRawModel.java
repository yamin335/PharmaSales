package com.fantasyapps.darmahealthcare.models.PuchInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchRawModel {

    @SerializedName("employee_id")
    @Expose
    private String emplyoeeId;

    @SerializedName("shift_id")
    @Expose
    private long shiftId;

    @SerializedName("in_time")
    @Expose
    private String inTime;

    @SerializedName("out_time")
    @Expose
    private String outTime;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("lng")
    @Expose
    private Double lng;

    public String getEmplyoeeId() {
        return emplyoeeId;
    }

    public void setEmplyoeeId(String emplyoeeId) {
        this.emplyoeeId = emplyoeeId;
    }

    public long getShiftId() {
        return shiftId;
    }

    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
