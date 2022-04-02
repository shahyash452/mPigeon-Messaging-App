package com.example.finalloginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalloginpage.databinding.ActivityGroupChatBinding;

public class GroupChat extends AppCompatActivity {

    ActivityGroupChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}