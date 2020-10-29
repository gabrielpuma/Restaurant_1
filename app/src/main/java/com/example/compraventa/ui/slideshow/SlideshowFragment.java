package com.example.compraventa.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compraventa.R;
import com.example.compraventa.ip;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SlideshowFragment extends Fragment {
    RecyclerView rec;
    LinearLayoutManager ln;
    AdapterUser adp;
    Context ctx;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        adp=new AdapterUser(ctx);
        ln=new LinearLayoutManager(ctx);
        rec=root.findViewById(R.id.usuariosRecycler);
        ctx=container.getContext();
        rec.setLayoutManager(ln);
        rec.setAdapter(adp);
        cargar();
        return root;
    }

    private void cargar() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(ip.ip+"/user/lista",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject ob=response.getJSONObject(i);
                        adp.add(new ItemUsuario(ob.getString("name"),ob.getString("_id")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
