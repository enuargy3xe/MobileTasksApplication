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

public class MyTasks extends Fragment {

    RecyclerView myTasksRecycler;
    private API mService;
    private List<Tasks> myTasksList;
    private String Status;
    private API changeStatusService;

    public MyTasks() {
        // Required empty public constructor
    }

    public static MyTasks newInstance() {
        MyTasks fragment = new MyTasks();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        mService = APIUtil.getMyTasks();

        myTasksList = new ArrayList<>();
        myTasksRecycler = view.findViewById(R.id.my_task_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myTasksRecycler.setLayoutManager(layoutManager);

        MyTaskAdapter adapter = new MyTaskAdapter(myTasksList);
        adapter.setOnItemClickListener(new MyTaskAdapter.OnItemClickListener() {
            @Override
            public void onChangeStatusClick(int position) {
                UpdateTaskStatus(position);
            }
        });

        myTasksRecycler.setAdapter(adapter);

        LoadMyTasks();

        return view;
    }

    public void LoadMyTasks(){
        mService.getMyTasks(MetaData.getUser_id(),"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    if(response.body().getSuccess() == 1){
                        myTasksList.addAll(response.body().getTasks());
                        myTasksRecycler.getAdapter().notifyDataSetChanged();
                    }
                    else{

                    }
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {

            }
        });
    }

    public void UpdateTaskStatus(int position){
        changeStatusService = APIUtil.changeStatus();
        if(myTasksList.get(position).getStatus().equals("new")){
            Status = "done";
        }
        else{
            Status = "new";
        }
        changeStatusService.changeTaskStatus(myTasksList.get(position).getTaskId(),
                Status,"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    if(response.body().getSuccess() == 1){
                        myTasksList.get(position).setStatus(Status);
                        myTasksRecycler.getAdapter().notifyDataSetChanged();
                    }
                    else{

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