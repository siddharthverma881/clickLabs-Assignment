package com.example.assignment6.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private Address address = new Address();
    private Company company = new Company();

    @SerializedName("username")
    private String mUsername;

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("website")
    private String mWebsite;

    private String street,suite,city,zipcode,companyName,companyCatchPhrase,companyBs;

    public User(String username, int id) {
        this.mUsername = username;
        this.mId = id;
    }

    public String getUserName() {
        return mUsername;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public String getStreet() {
        return address.getStreet();
    }

    public String getSuite() {
        return address.getSuite();
    }

    public String getCity() {
        return address.getCity();
    }

    public String getZipcode() {
        return address.getZipcode();
    }

    public String getCompanyName() {
        return company.getName();
    }

    public String getCompanyCatchPhrase() {
        return company.getCatchPhrase();
    }

    public String getCompanyBs() {
        return company.getBs();
    }
}
