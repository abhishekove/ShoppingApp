package com.example.weshoppers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
private EditText username,newpassword,name,phone;
private FirebaseAuth mAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=findViewById(R.id.number);
        newpassword=findViewById(R.id.newpassword);
        mAuth=FirebaseAuth.getInstance();
        phone=findViewById(R.id.phone);
        progressBar=findViewById(R.id.bar);

        name=findViewById(R.id.name1);
    }
    public void check(View view){
        String email=username.getText().toString();
        String password=newpassword.getText().toString();

        authenticate(email,password);
    }
    public void authenticate(String email,String password){
        if(name.getText().toString().isEmpty())
        {
            name.setError("Enter name");
            name.requestFocus();
            return;
        }
        if (phone.getText().toString().isEmpty()){
            phone.setError("Enter number");
            phone.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            username.setError("Enter Email");
            Toast.makeText(this,"Enter email",Toast.LENGTH_LONG).show();
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Enter valid Email");
            Toast.makeText(this,"Enter valid email",Toast.LENGTH_LONG).show();
            username.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            newpassword.setError("Enter Password");
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            newpassword.requestFocus();
            return;
        }
        if(password.length()<6){
            newpassword.setError("Enter Valid Password");
            Toast.makeText(this,"Enter valid password",Toast.LENGTH_LONG).show();
            newpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("users").child("name").push().setValue(new letsChat(name.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getUid(),phone.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Signup.this,"User Registerd Successfully",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else {
                    Toast.makeText(Signup.this,"Error occured",Toast.LENGTH_LONG).show();
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(Signup.this,"You are already registerd",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
