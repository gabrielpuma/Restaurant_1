package com.example.compraventa.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compraventa.R;
import com.example.compraventa.chat.Chat;

import java.util.ArrayList;

public class AdapterNotify extends RecyclerView.Adapter<AdapterNotify.NotifyHolder>{
    Context context;
    ArrayList<ItemNotify> mArray;
    AdapterNotify(Context c){
        context=c;
        mArray=new ArrayList<>();
    }
    void add(ItemNotify item){
        mArray.add(item);
        notifyItemInserted(mArray.indexOf(item));
    }
    @NonNull
    @Override
    public NotifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify_user,parent,false);
        NotifyHolder nf=new NotifyHolder(v);
        return nf;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyHolder holder, int position) {
        final ItemNotify it=mArray.get(position);
        holder.txt.setText(it.getTitulo());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(context, Chat.class);
                in.putExtra("idChat",it.getIdChat());
                in.putExtra("id",it.getIdProd());
                in.putExtra("responseId",it.getResponseId());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }


    class NotifyHolder extends RecyclerView.ViewHolder {
        TextView txt;
        View view;
        public NotifyHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.notifyName);
            view=itemView;
        }
    }
}

