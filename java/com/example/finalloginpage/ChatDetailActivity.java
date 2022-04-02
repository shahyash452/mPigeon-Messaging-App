package com.example.finalloginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalloginpage.databinding.ActivityChatDetailBinding;
import com.squareup.picasso.Picasso;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChatDetailActivity.this,dashBoard.class);
                startActivity(intent);
            }
        });
        String name=getIntent().getStringExtra("Name");
        String proimg=getIntent().getStringExtra("ProfileImage");
        Picasso.get().load(proimg).placeholder(R.drawable.avatar1).into(binding.msgImg);
        binding.msgName.setText(name);

    }
}