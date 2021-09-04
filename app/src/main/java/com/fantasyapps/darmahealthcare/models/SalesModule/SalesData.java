
package com.fantasyapps.darmahealthcare.models.SalesModule;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesData {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("customer_id")
    @Expose
    private long customerId;
    @SerializedName("salesman_id")
    @Expose
    private long salesmanId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("YourReference")
    @Expose
    private String yourReference;
    @SerializedName("OurReference")
    @Expose
    private String ourReference;
    @SerializedName("branch_id")
    @Expose
    private long branchId;
    @SerializedName("cart_total")
    @Expose
    private long cartTotal;
    @SerializedName("discount_amount")
    @Expose
    private double discountAmount;
    @SerializedName("discount")
    @Expose
    private long discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("tax")
    @Expose
    private long tax;
    @SerializedName("tax_amount")
    @Expose
    private Object taxAmount;
    @SerializedName("vat")
    @Expose
    private long vat;
    @SerializedName("vat_amount")
    @Expose
    private Object vatAmount;
    @SerializedName("grand_total")
    @Expose
    private double grandTotal;
    @SerializedName("buying_total")
    @Expose
    private long buyingTotal;
    @SerializedName("paid_amount")
    @Expose
    private long paidAmount;
    @SerializedName("due_amount")
    @Expose
    private long dueAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("branch")
    @Expose
    private Branch branch;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(long salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYourReference() {
        return yourReference;
    }

    public void setYourReference(String yourReference) {
        this.yourReference = yourReference;
    }

    public String getOurReference() {
        return ourReference;
    }

    public void setOurReference(String ourReference) {
        this.ourReference = ourReference;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public long getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(long cartTotal) {
        this.cartTotal = cartTotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
        this.tax = tax;
    }

    public Object getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Object taxAmount) {
        this.taxAmount = taxAmount;
    }

    public long getVat() {
        return vat;
    }

    public void setVat(long vat) {
        this.vat = vat;
    }

    public Object getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Object vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public long getBuyingTotal() {
        return buyingTotal;
    }

    public void setBuyingTotal(long buyingTotal) {
        this.buyingTotal = buyingTotal;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public long getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(long dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

}
