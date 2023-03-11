package com.example.mobile_tasks.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class Success {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("user")
    @Expose
    private List<User> user;
    @SerializedName("Contacts")
    @Expose
    private List<Contacts> contacts;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

}