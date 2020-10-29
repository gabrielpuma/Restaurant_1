package com.example.compraventa.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compraventa.R;
import com.example.compraventa.Vista;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.compraventa.ip.ip;

public class AdapterUser extends  RecyclerView.Adapter<AdapterUser.UserHolder>{
    Context context;
    ArrayList<ItemUsuario> mArray;
    AdapterUser(Context c){
        context=c;
        mArray=new ArrayList<>();
    }
    void add(ItemUsuario item){
        mArray.add(item);
        notifyItemInserted(mArray.indexOf(item));
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users_list,parent,false);
        UserHolder uh=new UserHolder(v);
        return uh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final ItemUsuario it=mArray.get(position);
        holder.txt.setText(it.getName());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(it.getId());
            }
        });
    }

    private void send(String id) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.patch(ip+"/user/update/"+id,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String res=response.getString("message");
                    //Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    //Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        TextView txt;
        Button btn;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.usuariosName);
            btn=itemView.findViewById(R.id.usuariosBtn);
        }
    }
}
