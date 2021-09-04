
package com.fantasyapps.darmahealthcare.models.SalesModule;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("sales")
    @Expose
    private Sales sales;
    @SerializedName("total")
    @Expose
    private Total total;
    @SerializedName("branches")
    @Expose
    private List<Branch_> branches = null;

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public List<Branch_> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch_> branches) {
        this.branches = branches;
    }

}
