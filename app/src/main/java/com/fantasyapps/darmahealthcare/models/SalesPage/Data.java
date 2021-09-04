
package com.fantasyapps.darmahealthcare.models.SalesPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("salesman_id")
    @Expose
    private Integer salesmanId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("YourReference")
    @Expose
    private Object yourReference;
    @SerializedName("OurReference")
    @Expose
    private String ourReference;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("cart_total")
    @Expose
    private Integer cartTotal;
    @SerializedName("discount_amount")
    @Expose
    private Object discountAmount;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("tax_amount")
    @Expose
    private Object taxAmount;
    @SerializedName("vat")
    @Expose
    private Integer vat;
    @SerializedName("vat_amount")
    @Expose
    private Object vatAmount;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("due_amount")
    @Expose
    private Integer dueAmount;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
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

    public Object getYourReference() {
        return yourReference;
    }

    public void setYourReference(Object yourReference) {
        this.yourReference = yourReference;
    }

    public String getOurReference() {
        return ourReference;
    }

    public void setOurReference(String ourReference) {
        this.ourReference = ourReference;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Integer cartTotal) {
        this.cartTotal = cartTotal;
    }

    public Object getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Object discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Object getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Object taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Object getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Object vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Integer dueAmount) {
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
