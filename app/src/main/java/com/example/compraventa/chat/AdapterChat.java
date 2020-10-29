package com.example.compraventa.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compraventa.R;

import java.util.ArrayList;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ChatHolder>{
    Context context;
    ArrayList<ItemChat> mArray;
    public AdapterChat(Context c){
        context=c;
        mArray=new ArrayList<>();
    }
    void add(ItemChat item){
        mArray.add(item);
        notifyItemInserted(mArray.indexOf(item));

    }
    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        ChatHolder ch=new ChatHolder(v);
        return ch;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ItemChat it=mArray.get(position);
        holder.name.setText(it.getName());
        holder.mess.setText(it.getMessage());
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder {
        TextView name,mess;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.chatName);
            mess=itemView.findViewById(R.id.chatMessage);
        }
    }
}
