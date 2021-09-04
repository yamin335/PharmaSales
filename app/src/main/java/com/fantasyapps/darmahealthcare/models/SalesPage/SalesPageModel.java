
package com.fantasyapps.darmahealthcare.models.SalesPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesPageModel {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("total")
    @Expose
    private Total total;
    @SerializedName("branches")
    @Expose
    private List<Branches> branches = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public List<Branches> getBranches() {
        return branches;
    }

    public void setBranches(List<Branches> branches) {
        this.branches = branches;
    }

}
