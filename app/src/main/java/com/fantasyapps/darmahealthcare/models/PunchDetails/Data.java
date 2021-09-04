package com.fantasyapps.darmahealthcare.models.PunchDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("attendances")
    @Expose
    private List<PunchDetailsModel> attendances = null;

    public List<PunchDetailsModel> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<PunchDetailsModel> attendances) {
        this.attendances = attendances;
    }

}
