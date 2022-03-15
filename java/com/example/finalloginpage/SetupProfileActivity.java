package com.example.finalloginpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetupProfileActivity extends AppCompatActivity {
    EditText name;
    Button submit;
    ImageView image;
    Uri selectedImage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);
        image=findViewById(R.id.profile);
        name=findViewById(R.id.inputName);
        submit=findViewById(R.id.completeProfile);
        database=FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Setting Profile..");
        dialog.setCancelable(false);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty())
                {
                    name.setError("Please type a Name");
                    return;
                }
                dialog.show();
                if(selectedImage!=null)
                {
                    StorageReference reference=storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();

                                            String uid = auth.getUid();
                                            String phone = auth.getCurrentUser().getPhoneNumber();
                                            String Username = name.getText().toString();


                                            Users user = new Users(uid, Username, phone, imageUrl);
                                            database.getReference()
                                                    .child("users")
                                                    .child(uid)
                                                    .setValue(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            dialog.dismiss();
                                                            Intent intent = new Intent(SetupProfileActivity.this, dashBoard.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });
                                    }
                                });
                            }

                        }
                    });
                }
                else{
                    String uid = auth.getUid();
                    String phone = auth.getCurrentUser().getPhoneNumber();
                    String Username = name.getText().toString();


                    Users user = new Users(uid, Username, phone, "No Image");
                    database.getReference()
                            .child("users")
                            .child(uid)
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(SetupProfileActivity.this, HelloWorld.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            if(data.getData() !=null)
            {
                image.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}