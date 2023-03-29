package com.example.mobile_tasks.data.model;

public class NewContactRequest {
    private String first_user_id;
    private String second_user_id;

    public String getFirst_user_id(){
        return first_user_id;
    }

    public String getSecond_user_id(){
        return second_user_id;
    }

    public void setFirst_user_id(String first_user_id){
        this.first_user_id = first_user_id;
    }

    public void setSecond_user_id(String second_user_id){
        this.second_user_id = second_user_id;
    }
}
