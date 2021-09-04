package com.fantasyapps.darmahealthcare.models.AddCustomer.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerRawModel {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("customer_type_id")
    @Expose
    private long customerTypeId;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("giveDiscount")
    @Expose
    private String giveDiscount;
    @SerializedName("discountBy")
    @Expose
    private String discountBy;
    @SerializedName("discountAmount")
    @Expose
    private double discountAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getGiveDiscount() {
        return giveDiscount;
    }

    public void setGiveDiscount(String giveDiscount) {
        this.giveDiscount = giveDiscount;
    }

    public String getDiscountBy() {
        return discountBy;
    }

    public void setDiscountBy(String discountBy) {
        this.discountBy = discountBy;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

}