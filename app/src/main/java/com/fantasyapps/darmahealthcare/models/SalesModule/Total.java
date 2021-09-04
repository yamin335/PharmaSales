
package com.fantasyapps.darmahealthcare.models.SalesModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total {

    @SerializedName("amount")
    @Expose
    private long amount;
    @SerializedName("vat")
    @Expose
    private long vat;
    @SerializedName("discount")
    @Expose
    private double discount;
    @SerializedName("total")
    @Expose
    private double total;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getVat() {
        return vat;
    }

    public void setVat(long vat) {
        this.vat = vat;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
