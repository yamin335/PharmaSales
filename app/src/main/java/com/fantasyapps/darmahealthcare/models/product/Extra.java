package com.fantasyapps.darmahealthcare.models.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra {

    @SerializedName("total_buying_price")
    @Expose
    private long totalBuyingPrice;
    @SerializedName("total_selling_price")
    @Expose
    private long totalSellingPrice;
    @SerializedName("total_mrp")
    @Expose
    private long totalMrp;

    public long getTotalBuyingPrice() {
        return totalBuyingPrice;
    }

    public void setTotalBuyingPrice(long totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
    }

    public long getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(long totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public long getTotalMrp() {
        return totalMrp;
    }

    public void setTotalMrp(long totalMrp) {
        this.totalMrp = totalMrp;
    }

}
