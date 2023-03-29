package com.example.mobile_tasks;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mobile_tasks.data.model.Tasks;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder> {

    private static final int UNSELECTED = -1;
    private List<Tasks> myTasksList;
    private OnItemClickListener mListener;
    private String avatarsPath = "http://m98299fy.beget.tech/Avatars/";
    private int selectedItem = UNSELECTED;

    public MyTaskAdapter(List<Tasks> myTasksList){
        this.myTasksList = myTasksList;
    }

    public interface OnItemClickListener{
        void onChangeStatusClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public MyTaskAdapter.MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_task_item,parent,false);
        MyTaskViewHolder viewHolder = new MyTaskViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskAdapter.MyTaskViewHolder holder, int position) {
        Tasks tasks = myTasksList.get(position);
        holder.receivedTaskSender.setText(tasks.getSurname() + " " + tasks.getName());
        holder.receivedTaskTittle.setText(tasks.getTaskTittle());
        holder.receivedTaskDetails.setText(Html.fromHtml(tasks.getTaskDetails()));
        if(tasks.getStatus().equals("new")){
            holder.receivedTaskStatus.setChecked(false);
        }
        else{
            holder.receivedTaskStatus.setChecked(true);
        }
        if(tasks.getUserImage().toString().isEmpty() || tasks.getUserImage() == null){
            holder.senderAvatar.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        else{
            GlideUrl url = new GlideUrl(avatarsPath + tasks.getUserImage().toString(),new LazyHeaders.Builder()
                    .addHeader("User-Agent","Mozilla/5.0")
                    .build());

            Glide.with(holder.senderAvatar.getContext())
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_search_24)
                    .into(holder.senderAvatar);
        }
        boolean isSelected = position == selectedItem;
        holder.itemView.setSelected(isSelected);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.receivedTaskRoot.collapse();
                if(position == selectedItem){
                    selectedItem = UNSELECTED;
                }
                else{
                    holder.itemView.setSelected(true);
                    holder.receivedTaskRoot.expand();
                    selectedItem = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTasksList.size();
    }

    class MyTaskViewHolder extends RecyclerView.ViewHolder{
        ImageView senderAvatar;
        TextView receivedTaskTittle;
        TextView receivedTaskSender;
        TextView receivedTaskDetails;
        SwitchCompat receivedTaskStatus;
        ExpandableLayout receivedTaskRoot;
        MyTaskViewHolder(View view, final OnItemClickListener listener){
            super(view);
            senderAvatar = view.findViewById(R.id.sender_user_avatar);
            receivedTaskTittle = view.findViewById(R.id.received_task_tittle);
            receivedTaskSender = view.findViewById(R.id.sender_user_name_text_view);
            receivedTaskDetails = view.findViewById(R.id.received_task_details);
            receivedTaskStatus = view.findViewById(R.id.received_task_status);
            receivedTaskRoot = view.findViewById(R.id.received_task_details_root);
            receivedTaskStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onChangeStatusClick(position);
                    }
                }
            });
        }
    }
}
