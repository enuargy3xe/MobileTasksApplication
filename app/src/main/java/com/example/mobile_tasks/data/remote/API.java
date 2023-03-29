package com.example.mobile_tasks.data.remote;

import com.example.mobile_tasks.data.model.NewContactRequest;
import com.example.mobile_tasks.data.model.NewTaskRequest;
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

    @GET("DB/get_find_user.php")
    Call<Success> getFindUser(@Query("finding_login") String findingLogin,@Header("User-Agent") String headerKey);

    @POST("DB/add_contact.php/")
    Call<Success> addNewContact(@Header("User-Agent") String headerKey,
                                @Body NewContactRequest newContactRequest);

    @POST("DB/delete_contact.php/")
    Call<Success> removeContact(@Header("User-Agent") String headerKey,
                                @Body NewContactRequest newContactRequest);

    @POST("DB/new_task.php/")
    Call<Success> addNewTask(@Header("User-Agent") String headerKey,
                             @Body NewTaskRequest newTaskRequest);

    @GET("DB/get_issued_tasks.php")
    Call<Success> getIssuedTasks(@Query("user_id") String sender,
                                 @Header("User-Agent") String headerKey);

    @GET("DB/get_my_tasks.php")
    Call<Success> getMyTasks(@Query("user_id") String receiver,
                             @Header("User-Agent") String headerKey);

    @GET("DB/update_task_status.php")
    Call<Success> changeTaskStatus(@Query("task_id") String taskId,
                                   @Query("status") String status,
                                   @Header("User-Agent") String headerKey);

    @FormUrlEncoded
    @POST("DB/update_user_image.php/")
    Call<String> updateUserAvatar(@Field("user_id") String user_id,
                                  @Field("image") String image,
                                  @Field("image_name") String imageName,
                                  @Header("User-Agent") String headerKey);
}
