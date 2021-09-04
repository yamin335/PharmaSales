
package com.fantasyapps.darmahealthcare.models.PunchDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PunchDetailsModel {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("overtime")
    @Expose
    private String overtime;
    @SerializedName("shift")
    @Expose
    private Shift shift;
    @SerializedName("late_time")
    @Expose
    private String lateTime;
    @SerializedName("is_late")
    @Expose
    private String isLate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("in_time")
    @Expose
    private String inTime;
    @SerializedName("in_times")
    @Expose
    private List<String> inTimes;
    @SerializedName("out_times")
    @Expose
    private List<String> outTimes;
    @SerializedName("out_time")
    @Expose
    private String outTime;
    @SerializedName("total_hour_worked")
    @Expose
    private String totalHourWorked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getLateTime() {
        return lateTime;
    }

    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }

    public String getIsLate() {
        return isLate;
    }

    public void setIsLate(String isLate) {
        this.isLate = isLate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public List<String> getInTimes() {
        return inTimes;
    }

    public void setInTimes(List<String> inTimes) {
        this.inTimes = inTimes;
    }

    public List<String> getOutTimes() {
        return outTimes;
    }

    public void setOutTimes(List<String> outTimes) {
        this.outTimes = outTimes;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getTotalHourWorked() {
        return totalHourWorked;
    }

    public void setTotalHourWorked(String totalHourWorked) {
        this.totalHourWorked = totalHourWorked;
    }

}
