package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {

    Button BtnSearchBook, BtnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        BtnSearchBook = findViewById(R.id.BtnSearchBook);
        BtnLogOut = findViewById(R.id.BtnLogOut);

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, SearchBookActivity.class));
            }
        });

        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, SelectUser.class));
            }
        });
    }

}