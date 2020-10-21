
package com.example.helpyar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter <chatAdapter.chatItemViewHolder>{

    private ArrayList<chatItem> mChatItemsList;

    public  class chatItemViewHolder extends  RecyclerView.ViewHolder{

        public TextView senderNameTv;
        public TextView chatMessageTv;
        public TextView timeTv;

        public chatItemViewHolder(@NonNull View itemView) {
            super(itemView);

            senderNameTv = itemView.findViewById(R.id.senderNameChatItem);
            chatMessageTv = itemView.findViewById(R.id.chatMessageChatItem);
            timeTv = itemView.findViewById(R.id.messageDateChatItem);

        }
    }

    public chatAdapter(ArrayList<chatItem> chatItemList){
        mChatItemsList = chatItemList;
    }
    @NonNull
    @Override
    public chatItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent,false);

        chatItemViewHolder cvh = new chatItemViewHolder(v);

        return  cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull chatItemViewHolder holder, int position) {

        chatItem currentItem = mChatItemsList.get(position);

        holder.senderNameTv.setText(currentItem.getSenderName());
        holder.chatMessageTv.setText(currentItem.getChatMessage());
        holder.timeTv.setText(currentItem.getMessage_date());


    }

    @Override
    public int getItemCount() {
        return mChatItemsList.size();
    }
}
