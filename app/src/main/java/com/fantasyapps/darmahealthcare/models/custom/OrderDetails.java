package com.fantasyapps.darmahealthcare.models.custom;

import java.util.ArrayList;
import java.util.List;


public class OrderDetails {

    public static String personName ="";

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<ProductDetails> getPlist() {
        return plist;
    }

    public void setPlist(List<ProductDetails> plist) {
        this.plist = plist;
    }

    List<ProductDetails> plist = new ArrayList<>();
}
