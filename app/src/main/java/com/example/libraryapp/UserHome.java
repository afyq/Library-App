package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {

    Button BtnSearchBook, BtnProfile, BtnAddNilam, BtnNilamRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        BtnSearchBook = findViewById(R.id.BtnSearchBook);
        BtnAddNilam = findViewById(R.id.BtnNilam);
        BtnNilamRecord = findViewById(R.id.BtnNilamRecord);
        BtnProfile = findViewById(R.id.BtnProfile);

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, UserSearchBookActivity.class));
            }
        });

        BtnAddNilam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, AddNilamActivity.class));
            }
        });

        BtnNilamRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, NilamRecordActivity.class));
            }
        });

        BtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, ProfileActivity.class));
            }
        });
    }

}