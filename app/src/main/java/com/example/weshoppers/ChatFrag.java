package com.example.weshoppers;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatFrag extends Fragment {
   private EditText editText;
   private ImageView imageView;
   private FirebaseUser firebaseUser;
   ImageView imageView2;
 String stat;
   private ListView listView;
    View v;
    DatabaseReference databaseReference;
    String chats;
    List<letsChat> chatList;

    @Override
    public void onStart() {
        super.onStart();
       if(firebaseUser.getUid().equals("QMXGCudkMWMxu0IrUF4qR47MHvg2"))
       {
           getU();
       }
       else {
           setChats();
       }
    }
    void getU()
    {
        databaseReference.child("users").child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot letsC : dataSnapshot.getChildren()) {
                    letsChat le = letsC.getValue(letsChat.class);
                    chatList.add(le);

                }
                ChatsList adapter = new ChatsList(getActivity(), chatList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String id=chatList.get(i).getUser();
                        stat=id;
                        setChats(id);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void setChats()
    {
        databaseReference.child("users").child(firebaseUser.getUid()).child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot letsC : dataSnapshot.getChildren()){
                    letsChat le=letsC.getValue(letsChat.class);
                    chatList.add(le);

                }
                ChatsList adapter=new ChatsList( getActivity(),chatList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void setChats(final String id)
    {
        databaseReference.child("users").child(id).child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot letsC : dataSnapshot.getChildren()){
                    letsChat le=letsC.getValue(letsChat.class);
                    chatList.add(le);

                }
                ChatsList adapter=new ChatsList( getActivity(),chatList);
                listView.setAdapter(adapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        databaseReference.child("users").child(id).child("chat").removeValue();
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.chat, container, false);
        editText = v.findViewById(R.id.text);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        imageView=v.findViewById(R.id.sendButton);
        listView=v.findViewById(R.id.list);
        imageView2=v.findViewById(R.id.gallery);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser.getUid().equals("QMXGCudkMWMxu0IrUF4qR47MHvg2"))
                {
                    getU();
                }
            }
        });
        databaseReference=FirebaseDatabase.getInstance().getReference();
        chatList=new ArrayList<>();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chats=editText.getText().toString();
                if(!TextUtils.isEmpty(editText.getText())) {
                    if (firebaseUser.getUid().equals("QMXGCudkMWMxu0IrUF4qR47MHvg2")) {
                        letsChat letsCha = new letsChat(editText.getText().toString(), firebaseUser.getEmail());
                        databaseReference.child("users").child(stat).child("chat").push().setValue(letsCha).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                editText.setText(" ");
                                Toast.makeText(getActivity(),"message sent",Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        letsChat letsCha = new letsChat(editText.getText().toString(), firebaseUser.getEmail());
                        databaseReference.child("users").child(firebaseUser.getUid()).child("chat").push().setValue(letsCha).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                editText.setText(" ");
                                Toast.makeText(getActivity(),"message sent",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

        return v;
    }
    public class kk extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {

        }
    }

}
