package com.fantasyapps.darmahealthcare.models.SalesModule.rawJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RawSalesModel {

    @SerializedName("column")
    @Expose
    private String column;
    @SerializedName("data")
    @Expose
    private long data;
    @SerializedName("filterDate")
    @Expose
    private FilterDate filterDate;
    @SerializedName("isDue")
    @Expose
    private boolean isDue;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public FilterDate getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(FilterDate filterDate) {
        this.filterDate = filterDate;
    }

    public boolean isIsDue() {
        return isDue;
    }

    public void setIsDue(boolean isDue) {
        this.isDue = isDue;
    }

}
