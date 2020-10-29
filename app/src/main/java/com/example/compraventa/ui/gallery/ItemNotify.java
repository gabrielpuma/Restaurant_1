package com.example.compraventa.ui.gallery;

public class ItemNotify {
    String titulo;
    String idChat;
    String idProd;
    String responseId;
    ItemNotify(String a,String b,String c,String d){
        titulo=a;
        idChat=b;
        idProd=c;
        responseId=d;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdChat() {
        return idChat;
    }

    public String getIdProd() {
        return idProd;
    }

    public String getResponseId() {
        return responseId;
    }
}
