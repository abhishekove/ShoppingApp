package com.example.weshoppers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
EditText userid,kpassword;
FirebaseAuth mAuth;
TextView textView;
FirebaseUser firebaseUser;
ProgressBar progressBar;
String email,use;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid=findViewById(R.id.userid);
        kpassword=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progress);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
    }

    public String getUserid() {
        return use;
    }

    public void login(View view){
         email=userid.getText().toString();
        String password=kpassword.getText().toString();


        if(email.isEmpty())
        {
            userid.setError("Enter Email");
            Toast.makeText(this,"Enter email",Toast.LENGTH_LONG).show();
            userid.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userid.setError("Enter valid Email");
            Toast.makeText(this,"Enter valid email",Toast.LENGTH_LONG).show();
            userid.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            kpassword.setError("Enter Password");
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            kpassword.requestFocus();
            return;
        }
        if(password.length()<6){
            kpassword.setError("Enter Valid Password");
            Toast.makeText(this,"Enter valid password",Toast.LENGTH_LONG).show();
            kpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
Intent intent=new Intent(MainActivity.this,Profile.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"Error occured",Toast.LENGTH_LONG).show();
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(MainActivity.this,"You are already registerd",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
