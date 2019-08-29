package com.example.weshoppers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Add extends AppCompatActivity {
    ImageView imageView;
    EditText title, description, price, offers;
    Button button;
    Uri uri;
    int count=0;
    String ili,string;
    StorageReference storageReference;
   private static final int Pick=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageView = findViewById(R.id.image);
        title = findViewById(R.id.Title);
        description = findViewById(R.id.Description);
        price = findViewById(R.id.Price);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        offers = findViewById(R.id.Offers);
        storageReference=FirebaseStorage.getInstance().getReference();
        button=findViewById(R.id.pushItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    void addItem() {
        if (TextUtils.isEmpty(title.getText())) {
            title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description.getText())) {
            description.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(price.getText())) {
            price.requestFocus();
            return;
        }



      final String key= FirebaseDatabase.getInstance().getReference().child("users").child("items").push().getKey();

      final StorageReference storageReference1=storageReference.child(key).child(System.currentTimeMillis()+"."+imfile(uri));
final  UploadTask uploadTask=storageReference1.putFile(uri);
      Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
          @Override
          public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
              if(!task.isSuccessful()){
                  throw task.getException();
              }
              ili=storageReference1.getDownloadUrl().toString();
              return storageReference1.getDownloadUrl();
          }
      }).addOnCompleteListener(new OnCompleteListener<Uri>() {
          @Override
          public void onComplete(@NonNull Task<Uri> task) {
              if (task.isSuccessful())
              {
                  ili=task.getResult().toString();
              }
          }
      });
      string=title.getText().toString();
      if (TextUtils.isEmpty(ili))
      {
          Toast.makeText(Add.this,"press once more",Toast.LENGTH_SHORT).show();
          return;
      }
        FirebaseDatabase.getInstance().getReference().child("users").child("items").child(key).setValue(new Items(ili,string,description.getText().toString(),price.getText().toString(),offers.getText().toString(),key));
    }
    void pickImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(intent,Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Pick && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uri=data.getData();
            imageView.setImageURI(uri);
        }
    }
    String imfile(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mp=MimeTypeMap.getSingleton();
        return  mp.getExtensionFromMimeType(cr.getType(uri));
    }
}
