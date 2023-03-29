package com.example.mobile_tasks.data.remote;

import com.example.mobile_tasks.data.model.Success;

public class APIUtil {
    public static final String BASE_URL = "http://m98299fy.beget.tech/";

    public static API getUser(){
        return RetrofitClient.getClient(BASE_URL).create(API.class);
    }

    public static API signUp(){
        return  RetrofitClient.getClient(BASE_URL).create(API.class);
    }

    public static API getContacts(){
        return RetrofitClient.getClient(BASE_URL).create(API.class);
    }

    public static API getFindUser(){
        return RetrofitClient.getClient(BASE_URL).create(API.class);
    }

    public static API addNewContact(){
        return RetrofitClient.getClient(BASE_URL).create(API.class);
    }

    public static API removeContact(){
        return RetrofitClient.getClient(BASE_URL).create(API.class);
    }

    public static API addNewTask(){return RetrofitClient.getClient(BASE_URL).create(API.class);}

    public static API getIssuedTasks(){return RetrofitClient.getClient(BASE_URL).create(API.class);}

    public static API getMyTasks(){return RetrofitClient.getClient(BASE_URL).create(API.class);}

    public static API changeStatus(){return RetrofitClient.getClient(BASE_URL).create(API.class);}

    public static API updateUserAvatar(){return RetrofitClient.getClient(BASE_URL).create(API.class);}
}
