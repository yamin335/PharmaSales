
package com.fantasyapps.darmahealthcare.models.SalesPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("vat")
    @Expose
    private Integer vat;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
