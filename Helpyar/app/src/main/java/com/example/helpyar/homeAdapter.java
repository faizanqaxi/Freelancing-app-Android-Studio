package com.example.helpyar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class homeAdapter extends RecyclerView.Adapter <homeAdapter.homeItemViewHolder>{

    private ArrayList<homeItem> mHomeItemsList;
    private homeAdapter.onLayoutItemClickListener monLayoutItemClickListener;

    public  class homeItemViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{

        public TextView titleTv;
        public TextView amountTv;
        public TextView statusTv;
        public TextView dateTv;
        public TextView sellerNameTv;
        public Button homeAcceptBtn;


        homeAdapter.onLayoutItemClickListener onLayoutItemClickListener;

        public homeItemViewHolder(@NonNull View itemView, final homeAdapter.onLayoutItemClickListener onLayoutItemClickListener) {
            super(itemView);

            sellerNameTv = itemView.findViewById(R.id.homeJobPostedBy);
            titleTv = itemView.findViewById(R.id.homeJobTitle);
            amountTv = itemView.findViewById(R.id.homeJobAmount);
            statusTv = itemView.findViewById(R.id.homeJobAssigned);
            dateTv = itemView.findViewById(R.id.homeJobDate);
            homeAcceptBtn = itemView.findViewById(R.id.homeAcceptBtn);

            this.onLayoutItemClickListener = onLayoutItemClickListener;

            homeAcceptBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    onLayoutItemClickListener.onAcceptClick(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            onLayoutItemClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public homeAdapter(ArrayList<homeItem> homeItemList, homeAdapter.onLayoutItemClickListener onLayoutItemClickListener){
        mHomeItemsList = homeItemList;
        monLayoutItemClickListener = onLayoutItemClickListener;

    }
    @NonNull
    @Override
    public homeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent,false);

        homeItemViewHolder hvh = new homeItemViewHolder(v, monLayoutItemClickListener);

        return  hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull homeItemViewHolder holder, int position) {

        homeItem currentItem = mHomeItemsList.get(position);

        holder.sellerNameTv.setText(currentItem.getJobPostedByUserName());
        holder.titleTv.setText(currentItem.getTitle());
        holder.amountTv.setText(currentItem.getAmount());
        holder.statusTv.setText(currentItem.getJobStatus());
        holder.dateTv.setText(currentItem.getPosted_date());


    }

    public interface onLayoutItemClickListener{
        void onNoteClick(int position);
        void onAcceptClick(int position);

    }

    @Override
    public int getItemCount() {
        return mHomeItemsList.size();
    }
}
