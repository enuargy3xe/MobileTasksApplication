package com.example.mobile_tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mobile_tasks.data.model.Contacts;
import com.example.mobile_tasks.data.remote.API;

import java.util.List;

public class ContactsFindAdapter extends RecyclerView.Adapter<ContactsFindAdapter.ViewHolder> {

    private String avatarsPath = "http://m98299fy.beget.tech/Avatars/";


    public List<Contacts> findedUserList;
    private OnItemClickListener mListener;

    public ContactsFindAdapter(List<Contacts> findedUserList){
        this.findedUserList = findedUserList;
    }

    public interface OnItemClickListener{
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public ContactsFindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_contacts_item,parent,false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ContactsFindAdapter.ViewHolder holder,int position){
        Contacts contacts = findedUserList.get(position);
        holder.userName.setText(contacts.getSurname() + " " + contacts.getName());
        holder.userPhone.setText(contacts.getLogin());
        if(contacts.getUserImage().toString().isEmpty()){
            holder.userAvatar.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        else{
            GlideUrl url = new GlideUrl(avatarsPath + contacts.getUserImage().toString(),new LazyHeaders.Builder()
                    .addHeader("User-Agent","Mozilla/5.0")
                    .build());

            Glide.with(holder.userAvatar.getContext())
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_search_24)
                    .into(holder.userAvatar);
        }
        holder.contacts = contacts;
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        if(findedUserList == null){
            return 0;
        }
        return findedUserList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userAvatar;
        TextView userName;
        TextView userPhone;
        ImageView addButton;
        Contacts contacts;
        int position;
        ViewHolder(View view, final OnItemClickListener listener){
            super(view);
            userAvatar = view.findViewById(R.id.userAvatar);
            userName = view.findViewById(R.id.user_name_text_view);
            userPhone = view.findViewById(R.id.user_phone_number_text_view);
            addButton = view.findViewById(R.id.add_contact);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onAddClick(position);
                    }
                }
            });
        }
    }
}
