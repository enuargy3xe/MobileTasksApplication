package com.example.mobile_tasks;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arefbhrn.maskededittext.MaskedEditText;
import com.example.mobile_tasks.data.model.RequestBody;
import com.example.mobile_tasks.data.model.Success;
import com.example.mobile_tasks.data.remote.API;
import com.example.mobile_tasks.data.remote.APIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText last_name_input,name_input,password_input,password_confirm_input;
    Button sign_up_button;
    MaskedEditText phone_input;


    private API mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        phone_input = findViewById(R.id.phone_input);
        last_name_input = findViewById(R.id.last_name_input);
        name_input = findViewById(R.id.name_input);
        password_input = findViewById(R.id.password_input);
        password_confirm_input = findViewById(R.id.password_confirm_input);

        sign_up_button = findViewById(R.id.sign_up_button);

        mService = APIUtil.signUp();

        password_confirm_input.addTextChangedListener(confirmPasswordListener);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp(){
        String login = phone_input.getRawText().toString().trim();
        String surname = last_name_input.getText().toString().trim();
        String name = name_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();

        RequestBody requestBody = new RequestBody();
        requestBody.setLogin(login);
        requestBody.setName(name);
        requestBody.setSurname(surname);
        requestBody.setPassword(password);

        mService.signUp("Mozilla/5.0",requestBody).enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    int success = response.body().getSuccess();
                    if(success == 1){
                        Toast.makeText(getApplicationContext(),"Регистрация прошла успешно",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Ошибка регистрации",Toast.LENGTH_LONG).show();
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

    TextWatcher confirmPasswordListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!password_input.getText().toString().equals(password_confirm_input.getText().toString())){
                password_confirm_input.setError("Пароли не совпадают");
                sign_up_button.setEnabled(false);
            }
            else{
                sign_up_button.setEnabled(true);
            }
        }
    };
}
