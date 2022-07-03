package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNilamActivity extends AppCompatActivity {

    EditText ETBookTitle, ETBookWriter, ETBookPublisher, ETBookLanguage, ETYearPublish, ETToRM;
    Button BtnAddNilam;
    DatabaseReference databaseNilam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nilam);

        databaseNilam = FirebaseDatabase.getInstance().getReference("Nilam");

        ETBookTitle = findViewById(R.id.ETBookTitle);
        ETBookWriter = findViewById(R.id.ETBookWriter);
        ETBookPublisher = findViewById(R.id.ETBookPublisher);
        ETBookLanguage = findViewById(R.id.ETBookLanguage);
        ETYearPublish = findViewById(R.id.ETYearPublish);
        ETToRM = findViewById(R.id.ETToRM);
        BtnAddNilam = findViewById(R.id.BtnAddNilam);

        BtnAddNilam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNilam();
            }
        });
    }

    private void addNilam() {
        String id = databaseNilam.push().getKey();
        String title = ETBookTitle.getText().toString().trim();
        String writer = ETBookWriter.getText().toString().trim();
        String publisher = ETBookPublisher.getText().toString().trim();
        String language = ETBookLanguage.getText().toString().trim();
        int year = Integer.parseInt(ETYearPublish.getText().toString().trim());
        String torm = ETToRM.getText().toString().trim();

        if(TextUtils.isEmpty(title)){
            Toast.makeText(this,"Please insert book title",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(writer)){
            Toast.makeText(this,"Please insert writer name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(publisher)){
            Toast.makeText(this,"Please insert publisher name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(language)){
            Toast.makeText(this,"Please insert book language",Toast.LENGTH_LONG).show();
            return;
        }
        if(year == 0){
            Toast.makeText(this,"Please insert book year publish",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(torm)){
            Toast.makeText(this,"Please insert type of reading material",Toast.LENGTH_LONG).show();
            return;
        }

        Nilam nilam = new Nilam(id, title, writer, publisher, language, year, torm);

        databaseNilam.child(id).setValue(nilam);

        ETBookTitle.getText().clear();
        ETBookWriter.getText().clear();
        ETBookPublisher.getText().clear();
        ETBookLanguage.getText().clear();
        ETYearPublish.getText().clear();
        ETToRM.getText().clear();

        Toast.makeText(this, "Nilam added", Toast.LENGTH_LONG).show();
    }

}