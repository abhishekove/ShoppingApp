package com.example.weshoppers;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeFrag extends Fragment {
    private List<Items> itemsList7=new ArrayList<>();
    private ListView listView;
    private FirebaseUser firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v= inflater.inflate(R.layout.store,container,false);
        listView=v.findViewById(R.id.list1);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("users").child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemsList7.clear();
                for (DataSnapshot dara:dataSnapshot.getChildren()){
                    Items items=dara.getValue(Items.class);
                    itemsList7.add(items);
                }
                ItemAdapter itemAdapter=new ItemAdapter(getActivity(),itemsList7);
                listView.setAdapter(itemAdapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (firebaseUser.getUid().equals("QMXGCudkMWMxu0IrUF4qR47MHvg2"))
                        {FirebaseDatabase.getInstance().getReference().child("users").child("items").child(itemsList7.get(i).getItemid()).removeValue();
                            FirebaseStorage.getInstance().getReference().child(itemsList7.get(i).getItemid()).child(itemsList7.get(i).getId1()).delete();
                        }
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
