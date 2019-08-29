package com.example.weshoppers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selected=null;
                switch (menuItem.getItemId()){
                    case R.id.shop:
                        selected=new HomeFrag();
                        break;
                    case R.id.chat:
                        selected=new ChatFrag();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement,selected).commit();
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragement,new HomeFrag()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       if (firebaseUser.getUid().equals("QMXGCudkMWMxu0IrUF4qR47MHvg2"))
       { getMenuInflater().inflate(R.menu.addfile,menu);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent=new Intent(Profile.this,Add.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }
}
