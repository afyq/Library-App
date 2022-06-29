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

public class AddBookActivity extends AppCompatActivity {

    EditText ETBookTitle, ETBookWriter, ETBookPublisher, ETBookGenre, ETBookPage, ETAvailability;
    Button BtnAddBook;
    DatabaseReference databaseBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        databaseBook = FirebaseDatabase.getInstance().getReference("Book");

        ETBookTitle = findViewById(R.id.ETBookTitle);
        ETBookWriter = findViewById(R.id.ETBookWriter);
        ETBookPublisher = findViewById(R.id.ETBookPublisher);
        ETBookGenre = findViewById(R.id.ETBookGenre);
        ETBookPage = findViewById(R.id.ETBookPage);
        ETAvailability = findViewById(R.id.ETAvailability);
        BtnAddBook = findViewById(R.id.BtnAddBook);

        BtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
    }

    private void addBook() {
        String id = databaseBook.push().getKey();
        String title = ETBookTitle.getText().toString().trim();
        String writer = ETBookWriter.getText().toString().trim();
        String publisher = ETBookPublisher.getText().toString().trim();
        String genre = ETBookGenre.getText().toString().trim();
        int page = Integer.parseInt(ETBookPage.getText().toString().trim());
        int available = Integer.parseInt(ETAvailability.getText().toString().trim());

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
        if(TextUtils.isEmpty(genre)){
            Toast.makeText(this,"Please insert book genre",Toast.LENGTH_LONG).show();
            return;
        }
        if(page == 0){
            Toast.makeText(this,"Please insert book page",Toast.LENGTH_LONG).show();
            return;
        }
        if(available == 0){
            Toast.makeText(this,"Please insert book availability",Toast.LENGTH_LONG).show();
            return;
        }

        Book book = new Book(id, title, writer, publisher, genre, page, available);

        databaseBook.child(id).setValue(book);

        ETBookTitle.getText().clear();
        ETBookWriter.getText().clear();
        ETBookPublisher.getText().clear();
        ETBookGenre.getText().clear();
        ETBookPage.getText().clear();
        ETAvailability.getText().clear();

        Toast.makeText(this, "Book added", Toast.LENGTH_LONG).show();
    }
}