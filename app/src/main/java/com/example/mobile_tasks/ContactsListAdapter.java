package com.example.mobile_tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mobile_tasks.data.model.Contacts;
import com.example.mobile_tasks.data.model.User;

import org.w3c.dom.Text;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    private String avatarsPath = "http://m98299fy.beget.tech/Avatars/";

    private List<Contacts> contactsList;

    public ContactsListAdapter(List<Contacts> contactsList){
        this.contactsList = contactsList;
    }
    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsListAdapter.ViewHolder holder, int position) {
        Contacts contact = contactsList.get(position);
        holder.userName.setText(contact.getSurname() + " " + contact.getName());
        holder.phoneNumber.setText(contact.getLogin());
        if(contact.getUserImage().toString().isEmpty()){
            holder.userAvatar.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        else{
            GlideUrl url = new GlideUrl(avatarsPath + contact.getUserImage().toString(),new LazyHeaders.Builder()
            .addHeader("User-Agent","Mozilla/5.0")
            .build());

            Glide.with(holder.userAvatar.getContext())
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_search_24)
                    .into(holder.userAvatar);
        }
    }

    @Override
    public int getItemCount() {
        if(contactsList == null){
            return 0;
        }
        return contactsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvatar;
        TextView userName;
        TextView phoneNumber;
        ViewHolder(View view){
            super(view);
            userAvatar = view.findViewById(R.id.userAvatar);
            userName = view.findViewById(R.id.user_name_text_view);
            phoneNumber = view.findViewById(R.id.user_phone_number_text_view);
        }
    }
}
