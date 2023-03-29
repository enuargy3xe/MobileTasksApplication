package com.example.mobile_tasks;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.example.mobile_tasks.data.helpers.MetaData;
import com.example.mobile_tasks.data.model.Contacts;
import com.example.mobile_tasks.data.model.NewTaskRequest;
import com.example.mobile_tasks.data.model.Success;
import com.example.mobile_tasks.data.remote.API;
import com.example.mobile_tasks.data.remote.APIUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AviableAContacts extends Fragment {

    private API mService;
    private API newTaskService;
    private RecyclerView aviableContactsList;
    List<Contacts> availiableContactsArray;
    EditText taskTittle;
    HorizontalScrollView toolBar;
    Button sendButton;
    private String selectedUserID;

    RichEditor editor;

    public AviableAContacts() {
        // Required empty public constructor
    }

    public static AviableAContacts newInstance() {
        AviableAContacts fragment = new AviableAContacts();
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
        View view = inflater.inflate(R.layout.fragment_aviable_a_contacts, container, false);

        taskTittle = view.findViewById(R.id.task_tittle);
        toolBar = view.findViewById(R.id.editor_toolbar);
        sendButton = view.findViewById(R.id.send_task);

        taskTittle.setVisibility(View.INVISIBLE);
        toolBar.setVisibility(View.INVISIBLE);
        sendButton.setVisibility(View.INVISIBLE);

        availiableContactsArray = new ArrayList<>();

        aviableContactsList = view.findViewById(R.id.aviableContactsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        aviableContactsList.setLayoutManager(layoutManager);

        mService = APIUtil.getContacts();

        AviliableContactsAdapter adapter = new AviliableContactsAdapter(availiableContactsArray);
        adapter.setOnItemCLickListener(new AviliableContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedUserID = availiableContactsArray.get(position).getUserId();
                taskTittle.setVisibility(View.VISIBLE);
                toolBar.setVisibility(View.VISIBLE);
                sendButton.setVisibility(View.VISIBLE);
                editor.setVisibility(View.VISIBLE);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendTask();
            }
        });

        aviableContactsList.setAdapter(adapter);
        loadContacts();

        editor = (RichEditor) view.findViewById(R.id.editor);
        editor.setEditorHeight(220);
        editor.setEditorFontSize(12);
        editor.setEditorFontColor(Color.BLACK);
        editor.setPadding(10,10,10,10);
        editor.setPlaceholder("Введите детали...");

        //listener for UNDO action
        view.findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.undo();
            }
        });

        //listener for REDO action
        view.findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.redo();
            }
        });

        //listener for set bold action
        view.findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBold();
            }
        });

        //listener for italic font
        view.findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setItalic();
            }
        });

        //listener for subscript
        view.findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setSubscript();
            }
        });

        //listener for supersript
        view.findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setSuperscript();
            }
        });

        //listener for strikethrough
        view.findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setStrikeThrough();
            }
        });

        //listener for underline
        view.findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setUnderline();
            }
        });

        //header1(h1)
        view.findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(1);
            }
        });
        //header2(h2)
        view.findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(2);
            }
        });
        //header3(h3)
        view.findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(3);
            }
        });
        //header4(h4)
        view.findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(4);
            }
        });
        //header5(h5)
        view.findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(5);
            }
        });
        //header6(h6)
        view.findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setHeading(6);
            }
        });

        //textColorChanging
        view.findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                editor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        //text bg color changing
        view.findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                editor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        //indent
        view.findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setIndent();
            }
        });

        //outdent
        view.findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setOutdent();
            }
        });

        //aling text
        view.findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignLeft();
            }
        });

        view.findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignCenter();
            }
        });

        view.findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignRight();
            }
        });

        //kavichki
        view.findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBlockquote();
            }
        });

        //Маркированный список
        view.findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBullets();
            }
        });

        //Нумерованный список
        view.findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setNumbers();
            }
        });

        //Вставки изображения
        view.findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertImage("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg",
                        "dachshund", 320);
            }
        });

        //Вставка ссылки
        view.findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert link code here
            }
        });

        //ЧекБОКС
        view.findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert image code here
            }
        });


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
                        availiableContactsArray.addAll(response.body().getContacts());
                        aviableContactsList.getAdapter().notifyDataSetChanged();
                    }
                    else{

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

    public void SendTask(){
        NewTaskRequest newTaskRequest = new NewTaskRequest();
        newTaskRequest.setSender(MetaData.getUser_id());
        newTaskRequest.setReciver(selectedUserID);
        newTaskRequest.setTask_tittle(taskTittle.getText().toString());
        newTaskRequest.setTask_details(editor.getHtml());

        newTaskService = APIUtil.addNewTask();

        newTaskService.addNewTask("Mozilla/5.0",newTaskRequest).enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if(response.isSuccessful()){
                    int success = response.body().getSuccess();
                    if(success == 1){
                        Toast.makeText(getContext(),"New task successfully created",Toast.LENGTH_SHORT).show();
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

}