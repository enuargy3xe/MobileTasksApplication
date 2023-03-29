package com.example.mobile_tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mobile_tasks.data.model.Contacts;

import java.util.List;

public class AviliableContactsAdapter extends RecyclerView.Adapter<AviliableContactsAdapter.AviliableContactsViewHolder> {

    private List<Contacts> availableContacts;
    private OnItemClickListener mListener;
    private String avatarsPath = "http://m98299fy.beget.tech/Avatars/";

    public AviliableContactsAdapter(List<Contacts> availableContacts){
        this.availableContacts = availableContacts;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemCLickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public AviliableContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.aviable_contact_item,parent,false);
        AviliableContactsViewHolder viewHolder = new AviliableContactsViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AviliableContactsViewHolder holder, int position) {
        Contacts contact = availableContacts.get(position);
        holder.availableContactSurname.setText(contact.getSurname());
        holder.availableContactName.setText(contact.getName());
        if(contact.getUserImage().toString().isEmpty()){
            holder.availableContactPhoto.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        else{
            GlideUrl url = new GlideUrl(avatarsPath + contact.getUserImage().toString(),new LazyHeaders.Builder()
                    .addHeader("User-Agent","Mozilla/5.0")
                    .build());

            Glide.with(holder.availableContactPhoto.getContext())
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_search_24)
                    .into(holder.availableContactPhoto);
            holder.position = position;
        }
    }

    @Override
    public int getItemCount() {
        return availableContacts.size();
    }

    class AviliableContactsViewHolder extends RecyclerView.ViewHolder{
        ImageView availableContactPhoto;
        TextView availableContactSurname;
        TextView availableContactName;
        Contacts contacts;
        int position;
        AviliableContactsViewHolder(View view, final OnItemClickListener listener){
            super(view);
            availableContactPhoto = view.findViewById(R.id.aviable_contact_image);
            availableContactSurname = view.findViewById(R.id.aviable_contact_surname);
            availableContactName = view.findViewById(R.id.aviable_contact_name);
            itemView.setOnClickListener((v) -> {
                if(listener != null){
                    int postion = getAdapterPosition();
                    if(postion != RecyclerView.NO_POSITION){
                        listener.onItemClick(postion);
                    }
                }
            });
        }

    }
}
