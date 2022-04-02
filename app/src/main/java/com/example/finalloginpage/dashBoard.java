package com.example.finalloginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalloginpage.Adapters.FragmentsAdapter;
import com.example.finalloginpage.databinding.ActivityDashBoardBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class dashBoard extends AppCompatActivity {

    ActivityDashBoardBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewPager);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pigeon_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
//                Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(dashBoard.this, Settings.class);
                startActivity(intent2);

                break;

            case R.id.log_out:
                auth.signOut();
                Intent intent = new Intent(dashBoard.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
                break;

            case R.id.group_chat:
                Intent intent1 = new Intent(dashBoard.this, GroupChat.class);
                startActivity(intent1);
                break;

        }
        return true;
    }
}

