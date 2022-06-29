package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button BtnAddBook, BtnSearchBook, BtnIssueBook, BtnReturnBook, BtnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnAddBook = findViewById(R.id.BtnAddBook);
        BtnSearchBook = findViewById(R.id.BtnSearchBook);
        BtnIssueBook = findViewById(R.id.BtnIssueBook);
        BtnReturnBook = findViewById(R.id.BtnReturnBook);
        BtnStudent = findViewById(R.id.BtnStudent);

        BtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBookActivity.class));
            }
        });

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchBookActivity.class));
            }
        });

        BtnIssueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IssueBookActivity.class));
            }
        });

        BtnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReturnBookActivity.class));
            }
        });

        BtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentActivity.class));
            }
        });
    }
}