package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {

    Button BtnAddBook, BtnSearchBook, BtnIssueBook, BtnReturnBook, BtnRecordHistory, BtnLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        firebaseAuth = FirebaseAuth.getInstance();

        BtnAddBook = findViewById(R.id.BtnAddBook);
        BtnSearchBook = findViewById(R.id.BtnSearchBook);
        BtnIssueBook = findViewById(R.id.BtnIssueBook);
        BtnReturnBook = findViewById(R.id.BtnReturnBook);
        BtnRecordHistory = findViewById(R.id.BtnRecordHistory);
        BtnLogout = findViewById(R.id.BtnLogout);


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

        BtnRecordHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, RecordHistoryActivity.class));
            }
        });

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(AdminHome.this, SelectUser.class));
            }
        });

    }

}