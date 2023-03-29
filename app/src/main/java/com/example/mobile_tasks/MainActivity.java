package com.example.mobile_tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arefbhrn.maskededittext.MaskedEditText;
import com.example.mobile_tasks.data.helpers.MetaData;
import com.example.mobile_tasks.data.model.Success;
import com.example.mobile_tasks.data.model.User;
import com.example.mobile_tasks.data.remote.API;
import com.example.mobile_tasks.data.remote.APIUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    Button loginButton;
    MaskedEditText login_field;
    EditText password_field;
    TextView signUpOffer;

    private API mService;

    private static String url_user_info = "http://m98299fy.beget.tech/DB/get_user_info.php";

    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.login_button);
        login_field = findViewById(R.id.login_field);
        password_field = findViewById(R.id.password_field);
        signUpOffer = findViewById(R.id.SignUpText);


        mService = APIUtil.getUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAnswer();
            }
        });

        signUpOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);

            }
        });
    }

    public void loadAnswer(){
        String login = login_field.getRawText().trim();
        String password = password_field.getText().toString().trim();
        mService.getUser(login,password,"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    int success = response.body().getSuccess();
                    if(success == 1){
                        userList = response.body().getUser();
                        Toast.makeText(getApplicationContext(),userList.get(0).getName().toString(),Toast.LENGTH_LONG).show();
                        MetaData.setUser_id(userList.get(0).getUserId().toString());
                        MetaData.setUser_name(userList.get(0).getName());
                        MetaData.setUser_surname(userList.get(0).getSurname());
                        MetaData.setUser_image(userList.get(0).getUserImage());
                        Intent successIntent = new Intent(getApplicationContext(),WorkSpaceActivity.class);
                        startActivity(successIntent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"User not found",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    //Для отладки
                    int statucCode = response.code();
                    Toast.makeText(getApplicationContext(),String.valueOf(statucCode),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {

            }
        });
    }

}

