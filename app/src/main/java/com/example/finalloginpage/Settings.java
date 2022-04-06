package com.example.finalloginpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalloginpage.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class Settings extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();



        database.getReference()
                .child("users")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users user = snapshot.getValue(Users.class);
                        Picasso.get()
                                .load(user.getProfileImage())
                                .placeholder(R.drawable.avatar)
                                .into(binding.userProfileImageSettings);

                        binding.aboutEditTextSettings.setText(user.getAbout());
                        binding.userNameEditTextSettings.setText(user.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.saveBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = binding.aboutEditTextSettings.getText().toString();
                String userName = binding.userNameEditTextSettings.getText().toString();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("name", userName);
                obj.put("about", status);

                database.getReference().child("users").child(auth.getUid()).updateChildren(obj);
                Toast.makeText(Settings.this, "Updated...", Toast.LENGTH_SHORT).show();
            }
        });

        binding.backArrowSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, dashBoard.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        //HELLO

        binding.plusSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1909);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null){
            Uri profilePhotoSettings = data.getData();
            binding.userProfileImageSettings.setImageURI(profilePhotoSettings);

            StorageReference reference=storage.getReference().child("Profiles").child(auth.getUid());
            reference.putFile(profilePhotoSettings).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(Settings.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                            database.getReference()
                                    .child("users")
                                    .child(FirebaseAuth.getInstance().getUid()).child("profileImage")
                                    .setValue(uri.toString());
                        }
                    });
                }
            });
        }
    }
}