
package com.fantasyapps.darmahealthcare.models.customerType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("customerType")
    @Expose
    private List<CustomerType> customerType = null;

    public List<CustomerType> getCustomerType() {
        return customerType;
    }

    public void setCustomerType(List<CustomerType> customerType) {
        this.customerType = customerType;
    }
}
