package com.example.weshoppers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;


import java.util.List;

public class ChatsList extends ArrayAdapter<letsChat> {
    private Activity context;
    List<letsChat> chatList;

    public ChatsList(Activity context,List<letsChat> chatList){
        super(context,R.layout.adapterexample,chatList);
        this.context=context;
        this.chatList=chatList;
    }
public void atEnd(List<letsChat> chatLi)
{
    chatList.addAll(chatLi);
    this.notifyDataSetChanged();
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.adapterexample,null,true);
        TextView chatText=view.findViewById(R.id.content);
        TextView chatName=view.findViewById(R.id.name);
        letsChat lets=chatList.get(position);
        chatText.setText(lets.getChat());
        chatName.setText(lets.getUser());
        return view;
    }
}
