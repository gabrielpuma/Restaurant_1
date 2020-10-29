package com.example.compraventa.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
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

public class GalleryFragment extends Fragment {

    RecyclerView rec;
    LinearLayoutManager lnm;
    AdapterNotify adp;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        rec=root.findViewById(R.id.notifyrecycler);
        lnm=new LinearLayoutManager(context);
        adp=new AdapterNotify(context);
        rec.setLayoutManager(lnm);
        rec.setAdapter(adp);

        load();

        return root;
    }

    private void load() {
        SharedPreferences pref=getActivity().getSharedPreferences("datauser", Context.MODE_PRIVATE);
        String id=pref.getString("idUser","");
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(ip.ip+"/user/noty/"+id,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj=response.getJSONObject(i);
                        String a=obj.getString("titulo");
                        String b=obj.getString("idProd");
                        String c=obj.getString("idChat");
                        String d=obj.getString("idUs");
                        ItemNotify item=new ItemNotify(a,c,b,d);
                        adp.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
