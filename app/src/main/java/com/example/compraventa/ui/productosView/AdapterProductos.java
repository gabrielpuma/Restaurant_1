package com.example.compraventa.ui.productosView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compraventa.R;

import java.util.ArrayList;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ProdHolder>{
    Context context;
    ArrayList<ItemProducto> mArray;
    AdapterProductos(Context c){
        context=c;
        mArray=new ArrayList<>();

    }
    @NonNull
    @Override
    public ProdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_list,parent,false);
        ProdHolder ph=new ProdHolder(v);
        return ph;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdHolder holder, int position) {
        ItemProducto it=mArray.get(position);
        holder.txt.setText(it.getTitulo());
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    class ProdHolder extends RecyclerView.ViewHolder {
        TextView txt;
        Button btn;
        public ProdHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.productosName);
            btn=itemView.findViewById(R.id.productosBtn);
        }
    }
}
