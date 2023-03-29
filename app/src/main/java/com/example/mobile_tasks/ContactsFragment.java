package com.example.mobile_tasks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arefbhrn.maskededittext.MaskedEditText;
import com.example.mobile_tasks.data.helpers.MetaData;
import com.example.mobile_tasks.data.model.Contacts;

import java.util.ArrayList;
import java.util.List;

import com.example.mobile_tasks.data.model.NewContactRequest;
import com.example.mobile_tasks.data.model.RequestBody;
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
    List<Contacts> findArray;

    private API mService;
    private API secondService;
    private API addContactService;
    private API removeContact;

    Boolean buttonClicked = false;

    MaskedEditText searchInputField;
    Button startSearch;

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

        searchInputField = view.findViewById(R.id.search_contacts_input);
        startSearch = view.findViewById(R.id.start_search_button);
        startSearch.setEnabled(false);



        mService = APIUtil.getContacts();
        secondService = APIUtil.getFindUser();
        addContactService = APIUtil.getContacts();
        removeContact = APIUtil.removeContact();

        contactsArray = new ArrayList<>();
        findArray = new ArrayList<>();

        Contacts manuallyContacts = new Contacts();

        contactsList = view.findViewById(R.id.contacts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contactsList.setLayoutManager(layoutManager);

        ContactsListAdapter adapter = new ContactsListAdapter(contactsArray);
        adapter.setOnItemCLickListener(new ContactsListAdapter.OnItemClickListener() {
            @Override
            public void onRemoveClick(int position) {
                NewContactRequest removeRequest = new NewContactRequest();
                removeRequest.setFirst_user_id(MetaData.getUser_id());
                removeRequest.setSecond_user_id(contactsArray.get(position).getUserId().toString());
                removeContact.removeContact("Mozilla/5.0",removeRequest).enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(response.isSuccessful()){
                            int success = response.body().getSuccess();
                            if(success == 1){
                                contactsArray.remove(position);
                                adapter.notifyItemRemoved(position);
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
        });

        ContactsFindAdapter findAdapter = new ContactsFindAdapter(findArray);
        findAdapter.setOnItemClickListener(new ContactsFindAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                NewContactRequest newContactRequest = new NewContactRequest();
                newContactRequest.setFirst_user_id(MetaData.getUser_id());
                newContactRequest.setSecond_user_id(findAdapter.findedUserList.get(position).getUserId().toString());
                addContactService.addNewContact("Mozilla/5.0",newContactRequest).enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(response.isSuccessful()){
                            int success = response.body().getSuccess();
                            if(success == 1){
                                contactsArray.add(findArray.get(position));
                                adapter.notifyDataSetChanged();
                                findArray.remove(position);
                                findAdapter.notifyItemRemoved(position);
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
        });


        contactsList.setAdapter(adapter);

        searchInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(searchInputField.getRawText().length() == 10){
                    startSearch.setEnabled(true);
                }
                else{
                    if(buttonClicked){
                        contactsList.setAdapter(adapter);
                        buttonClicked = false;
                    }

                }
            }
        });

        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked = true;
                laodFindResult(searchInputField.getRawText(),findAdapter);
            }
        });

        loadContacts();

        return view;

    }

    //Отображение добавленных контактов
    public void loadContacts(){

        String user_id = MetaData.getUser_id();
        mService.getContacts(user_id,"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    if(response.body().getContacts() != null){
                        contactsArray.addAll(response.body().getContacts());
                        contactsList.getAdapter().notifyDataSetChanged();
                    }
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

    //отображение результатов поиска пользователя
    public void laodFindResult(String str, RecyclerView.Adapter adapter){
        secondService.getFindUser(str,"Mozilla/5.0").enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    int succesID = response.body().getSuccess();
                    if(succesID == 1){
                        findArray.clear();
                        findArray.addAll(response.body().getContacts());
                        contactsList.setAdapter(adapter);
                        contactsList.getAdapter().notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getContext(),"Error!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    int statusCode = response.code();
                    Toast.makeText(getContext(),String.valueOf(statusCode),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {

            }
        });

    }
}