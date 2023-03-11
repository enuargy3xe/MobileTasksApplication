package com.example.mobile_tasks.data.remote;

import com.example.mobile_tasks.data.model.RequestBody;
import com.example.mobile_tasks.data.model.Success;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @GET("DB/sign_in.php")
    Call<Success> getUser(@Query("login") String login,@Query("password") String password, @Header("User-Agent") String headerKey);

    @POST("DB/sign_up.php/")
    Call<Success> signUp(@Header("User-Agent") String headerKey,
                         @Body RequestBody requestBody);

    @GET("DB/get_user_contacts.php")
    Call<Success> getContacts(@Query("user_id") String user_id,@Header("User-Agent") String headerKey);
}
