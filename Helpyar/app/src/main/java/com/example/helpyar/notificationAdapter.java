package com.example.helpyar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class notificationAdapter extends RecyclerView.Adapter <notificationAdapter.notificationItemViewHolder>{

    private ArrayList<notificationItem> mNotificationList;
    private onLayoutItemClickListener monLayoutItemClickListener;

    public  class notificationItemViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{

        public TextView notificationUserTv;
        public TextView notificationTextTv;
        public TextView notificationDateTv;

        public LinearLayout notiLinLay;

        onLayoutItemClickListener onLayoutItemClickListener;

        public notificationItemViewHolder(@NonNull View itemView,onLayoutItemClickListener onLayoutItemClickListener) {
            super(itemView);

            notificationUserTv = itemView.findViewById(R.id.notificationBy);
            notificationTextTv = itemView.findViewById(R.id.notificationText);
            notificationDateTv = itemView.findViewById(R.id.notificationDate);
            notiLinLay = itemView.findViewById(R.id.notificationItemlinearlayout) ;

            this.onLayoutItemClickListener = onLayoutItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onLayoutItemClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public notificationAdapter(ArrayList<notificationItem> notificationList,onLayoutItemClickListener onLayoutItemClickListener){
        mNotificationList = notificationList;
        monLayoutItemClickListener = onLayoutItemClickListener;
    }
    @NonNull
    @Override
    public notificationItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent,false);

        notificationItemViewHolder nvh = new notificationItemViewHolder(v, monLayoutItemClickListener);

        return  nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull notificationItemViewHolder holder, int position) {



        final notificationItem currentItem = mNotificationList.get(position);
        final String notificationUserId;

        holder.notificationUserTv.setText(currentItem.getNotificationByUserName());
        holder.notificationTextTv.setText(currentItem.getNotificationText());
        holder.notificationDateTv.setText(currentItem.getNotification_date());

    }
    public interface onLayoutItemClickListener{
        void onNoteClick(int position);
    }
    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }
}
