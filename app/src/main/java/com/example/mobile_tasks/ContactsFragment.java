package com.example.mobile_tasks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mobile_tasks.data.helpers.MetaData;
import com.example.mobile_tasks.data.model.Contacts;

import java.util.ArrayList;
import java.util.List;

import com.example.mobile_tasks.data.model.Success;
import com.example.mobile_tasks.data.model.User;
import com.example.mobile_tasks.data.remote.API;
import com.example.mobile_tasks.data.remote.APIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsFragment extends Fragment {

    RecyclerView contactsList;
    List<Contacts> contactsArray;
    private API mService;



    public ContactsFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mService = APIUtil.getContacts();

        contactsArray = new ArrayList<>();

        Contacts manuallyContacts = new Contacts();

        contactsList = view.findViewById(R.id.contacts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contactsList.setLayoutManager(layoutManager);

        ContactsListAdapter adapter = new ContactsListAdapter(contactsArray);
        contactsList.setAdapter(adapter);

        loadContacts();

        return view;

    }

    public void loadContacts(){
        String user_id = MetaData.getUser_id();
        mService.getContacts(user_id,"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){

                    contactsArray.add(response.body().getContacts().get(0));
                    //contacts.addAll(response.body().getContacts());
                    contactsList.getAdapter().notifyDataSetChanged();
                    int succesID = response.body().getSuccess();
                }
                else{
                    int statucCode = response.code();
                    Toast.makeText(getContext(),String.valueOf(statucCode),Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<Success> call, Throwable t) {

            }
        });
    }
}