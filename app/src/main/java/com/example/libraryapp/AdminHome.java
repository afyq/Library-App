package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {

    Button BtnAddBook, BtnSearchBook, BtnIssueBook, BtnReturnBook, BtnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        BtnAddBook = findViewById(R.id.BtnAddBook);
        BtnSearchBook = findViewById(R.id.BtnSearchBook);
        BtnIssueBook = findViewById(R.id.BtnIssueBook);
        BtnReturnBook = findViewById(R.id.BtnReturnBook);
        BtnLogOut = findViewById(R.id.BtnLogOut);

        BtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AddBookActivity.class));
            }
        });

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, SearchBookActivity.class));
            }
        });

        BtnIssueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, IssueBookActivity.class));
            }
        });

        BtnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, ReturnBookActivity.class));
            }
        });

        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, SelectUser.class));
            }
        });
    }

}