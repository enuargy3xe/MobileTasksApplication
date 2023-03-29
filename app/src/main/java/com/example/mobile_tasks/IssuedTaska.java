package com.example.mobile_tasks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_tasks.data.helpers.MetaData;
import com.example.mobile_tasks.data.model.Success;
import com.example.mobile_tasks.data.model.Tasks;
import com.example.mobile_tasks.data.remote.API;
import com.example.mobile_tasks.data.remote.APIUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssuedTaska extends Fragment {

    RecyclerView issuedTasksList;
    private API mService;
    private List<Tasks> issuedTasksArray;

    public IssuedTaska() {
        // Required empty public constructor
    }

    public static IssuedTaska newInstance() {
        IssuedTaska fragment = new IssuedTaska();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_issued_taska, container, false);

        mService = APIUtil.getIssuedTasks();
        issuedTasksArray = new ArrayList<>();

        issuedTasksList = view.findViewById(R.id.issued_tasks_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        issuedTasksList.setLayoutManager(layoutManager);

        IssuedTasksAdapter adapter = new IssuedTasksAdapter(issuedTasksArray);


        issuedTasksList.setAdapter(adapter);

        LoadIssuedTasks();

        return view;
    }

    public void LoadIssuedTasks(){
        mService.getIssuedTasks(MetaData.getUser_id(),"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    if(response.body().getSuccess() == 1){
                        issuedTasksArray.addAll(response.body().getTasks());
                        issuedTasksList.getAdapter().notifyDataSetChanged();
                    }
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {

            }
        });
    }
}