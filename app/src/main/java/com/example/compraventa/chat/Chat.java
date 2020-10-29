package com.example.compraventa.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compraventa.MainActivity;
import com.example.compraventa.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;

import static com.example.compraventa.ip.ip;

public class Chat extends AppCompatActivity {
    Socket socket;
    RecyclerView rec;
    EditText message;
    LinearLayoutManager ln;
    public AdapterChat adp;
    Button btn;
    String idPr;
    String idUs;
    String name;
    String idChat="";
    String responseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            socket= IO.socket(ip);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Bundle b=getIntent().getExtras();
        idPr=b.getString("id");

        try {
            idChat=b.getString("idChat");
            responseId=b.getString("responseId");
        }catch (Exception e){
            //idChat="";
            //responseId="";
        }


        SharedPreferences pref=this.getSharedPreferences("datauser", Context.MODE_PRIVATE);
        idUs=pref.getString("idUser","");
        name=pref.getString("name","");

        if(responseId!=null){
            JSONObject ob=new JSONObject();
            try {
                ob.put("idPr",idPr);
                ob.put("responseId",responseId);
                ob.put("idUs",idUs);
                ob.put("socketid",socket.id());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("update",ob);
        }

        rec=findViewById(R.id.campoChatRecycler);
        ln=new LinearLayoutManager(this);
        adp=new AdapterChat(this);
        rec.setLayoutManager(ln);
        rec.setAdapter(adp);

        message=findViewById(R.id.campoChatMessage);
        btn=findViewById(R.id.campoChatSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Chat.this, "btn", Toast.LENGTH_SHORT).show();
                JSONObject ob=new JSONObject();
                try {
                    ob.put("idPr",idPr);
                    ob.put("idUs",idUs);
                    ob.put("name",name);
                    ob.put("message",message.getText().toString());
                    ob.put("socketid",socket.id());
                    if(idChat!=null){
                        ob.put("idChat",idChat);
                        ob.put("responseId",responseId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("chat",ob);
                message.setText("");
            }
        });

        socket.on("chat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject ob= (JSONObject) args[0];
                        try {
                            adp.add(new ItemChat(ob.getString("name"),ob.getString("message")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        load();
    }

    private void load() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();
        req.put("idPr",idPr);
        if(idChat!=null){
            req.put("idChat",idChat);
            client.post(ip+"/prod/chatfilter/",req,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject ob=response.getJSONObject(i);
                            adp.add(new ItemChat(ob.getString("name"),ob.getString("message")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            req.put("idUs",idUs);
            client.post(ip+"/prod/userfilter/",req,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject ob=response.getJSONObject(i);
                            adp.add(new ItemChat(ob.getString("name"),ob.getString("message")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }
}
