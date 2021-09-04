package com.fantasyapps.darmahealthcare.models.PunchReqRaw;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchReqModel {

    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("shift_id")
    @Expose
    private long shiftId;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;

    /**
     * No args constructor for use in serialization
     *
     */
    public PunchReqModel() {
    }

    /**
     *
     * @param employeeId
     * @param start
     * @param shiftId
     * @param end
     */
    public PunchReqModel(String start, String end, int shiftId, String employeeId) {
        super();
        this.start = start;
        this.end = end;
        this.shiftId = shiftId;
        this.employeeId = employeeId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getShiftId() {
        return shiftId;
    }

    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}