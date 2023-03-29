package com.example.mobile_tasks;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mobile_tasks.data.model.Tasks;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

import java.util.List;

public class IssuedTasksAdapter extends RecyclerView.Adapter<IssuedTasksAdapter.IssuedTasksViewHolder> {

    private static final int UNSELECTED = -1;
    private List<Tasks> tasksList;
    private OnItemClickListener mListener;
    private String avatarsPath = "http://m98299fy.beget.tech/Avatars/";
    private int selectedItem = UNSELECTED;

    public IssuedTasksAdapter(List<Tasks> tasksList){
        this.tasksList = tasksList;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOmItemClickListener(OnItemClickListener listener){mListener = listener;}

    @Override
    public IssuedTasksViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.issued_task_item,parent,false);
        IssuedTasksViewHolder viewHolder = new IssuedTasksViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IssuedTasksViewHolder holder,int position){
        Tasks tasks = tasksList.get(position);
        holder.issuedTaskReciver.setText(tasks.getSurname() + " " + tasks.getName());
        holder.issuedTaskTittle.setText(tasks.getTaskTittle());
        holder.issuedTaskDetails.setText(Html.fromHtml(tasks.getTaskDetails()));
        if(tasks.getStatus().equals("new")){
            holder.issuedTaskStatus.setImageResource(R.drawable.ic_baseline_hdr_strong_24);
        }
        else{
            holder.issuedTaskStatus.setImageResource(R.drawable.ic_baseline_done_24);
        }
        if(tasks.getUserImage().toString().isEmpty()){
            holder.issuedTasReciverAvatar.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        else{
            GlideUrl url = new GlideUrl(avatarsPath + tasks.getUserImage().toString(),new LazyHeaders.Builder()
                    .addHeader("User-Agent","Mozilla/5.0")
                    .build());

            Glide.with(holder.issuedTasReciverAvatar.getContext())
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_search_24)
                    .into(holder.issuedTasReciverAvatar);
        }
        boolean isSelected = position == selectedItem;
        holder.itemView.setSelected(isSelected);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.detailsRoot.collapse();
                if(position == selectedItem){
                    selectedItem = UNSELECTED;
                }
                else{
                    holder.itemView.setSelected(true);
                    holder.detailsRoot.expand();
                    selectedItem = position;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    class IssuedTasksViewHolder extends RecyclerView.ViewHolder{
        ImageView issuedTasReciverAvatar;
        TextView issuedTaskTittle;
        TextView issuedTaskReciver;
        TextView issuedTaskDetails;
        ImageView issuedTaskStatus;
        ExpandableLayout detailsRoot;
        IssuedTasksViewHolder(View view, final OnItemClickListener listener){
            super(view);
            issuedTasReciverAvatar = view.findViewById(R.id.issued_tasks_user_avatar);
            issuedTaskTittle = view.findViewById(R.id.issued_task_tittle);
            issuedTaskReciver = view.findViewById(R.id.issued_task_user_name_text_view);
            issuedTaskDetails = view.findViewById(R.id.issued_task_details);
            issuedTaskStatus = view.findViewById(R.id.issued_task_status);
            detailsRoot = view.findViewById(R.id.task_details_root);

        }
    }
}
