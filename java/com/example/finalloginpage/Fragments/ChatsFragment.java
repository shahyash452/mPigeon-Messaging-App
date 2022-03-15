package com.example.finalloginpage.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalloginpage.Adapters.UsersAdapter;
import com.example.finalloginpage.R;
import com.example.finalloginpage.Users;
import com.example.finalloginpage.databinding.FragmentChatsBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    FragmentChatsBinding binding;
    FirebaseDatabase database;
    ArrayList<Users> list=new ArrayList<>();
//    UsersAdapter usersAdapter;
    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatsBinding.inflate(inflater, container, false);
        database=FirebaseDatabase.getInstance();
//        users= new ArrayList<User>();
        UsersAdapter usersAdapter=new UsersAdapter(list,getContext());
        binding.rcv.setAdapter(usersAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(linearLayoutManager);

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Users users =dataSnapshot.getValue(Users.class);
                    users.getUid(dataSnapshot.getKey());
                    list.add(users);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}