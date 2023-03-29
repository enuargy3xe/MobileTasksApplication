package com.example.mobile_tasks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.mobile_tasks.data.helpers.MetaData;
import com.example.mobile_tasks.data.remote.API;
import com.example.mobile_tasks.data.remote.APIUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    ImageView Avatar;
    TextView Surname;
    TextView Name;
    private String avatarsPath = "http://m98299fy.beget.tech/Avatars/";
    static final int GALLERY_REQUEST = 1;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Avatar = view.findViewById(R.id.Avatar);
        Surname = view.findViewById(R.id.Surname);
        Name = view.findViewById(R.id.Name);

        Surname.setText(MetaData.getUser_surname());
        Name.setText(MetaData.getUser_name());

        GlideUrl url = new GlideUrl(avatarsPath + MetaData.getUser_image()
                .toString(),new LazyHeaders.Builder()
                .addHeader("User-Agent","Mozilla/5.0")
                .build());

        Glide.with(getContext())
                .load(url)
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_search_24)
                .centerCrop()
                .into(Avatar);

        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST){
            if(data != null){
                Uri contentUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentUri);

                    Avatar.setImageBitmap(bitmap);
                    Avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    UploadImage(bitmap);
                }
                catch (IOException e){
                    e.printStackTrace();

                }
            }
        }
    }

    public void UploadImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

        Random rnd = new Random();
        String imageName = String.valueOf(rnd.nextInt(1000) + 1) + "_" + rnd.nextInt(9998) + 1;

        API api = APIUtil.updateUserAvatar();
        api.updateUserAvatar(MetaData.getUser_id(),encodedImage,imageName,"Mozilla/5.0").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Toast.makeText(getContext(),"Image updated",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}