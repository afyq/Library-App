package com.example.libraryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserSearchBookActivity extends AppCompatActivity {

    DatabaseReference databaseBook;

    EditText ETSearchBook;
    Button BtnSearchBook;

    ListView listViewBook;

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_book);

        //getting the reference of letters node
        databaseBook = FirebaseDatabase.getInstance().getReference("Book");

        //getting listviews
        listViewBook = (ListView) findViewById(R.id.listViewBook);

        ETSearchBook = findViewById(R.id.ETSearchBook);
        BtnSearchBook = (Button) findViewById(R.id.BtnSearchBook);

        //list to store letters
        books = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attaching value event listener
                databaseBook.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String SearchBook = ETSearchBook.getText().toString().trim();

                        //clearing the previous list
                        books.clear();

                        if (!TextUtils.isEmpty(SearchBook)) {
                            //iterating through all the nodes
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //getting letter
                                Book book = postSnapshot.getValue(Book.class);

                                if ((book.getBookTitle() != null && book.getBookTitle().toLowerCase().contains(SearchBook.toLowerCase()))
                                        || (book.getBookWriter() != null && book.getBookWriter().toLowerCase().contains(SearchBook.toLowerCase()))
                                        || (book.getBookPublisher() != null && book.getBookPublisher().toLowerCase().contains(SearchBook.toLowerCase()))
                                        || (book.getBookGenre() != null && book.getBookGenre().toLowerCase().contains(SearchBook.toLowerCase()))) {
                                    //adding letter to the list
                                    books.add(book);
                                }
                            }

                            //creating adapter
                            BookList bookAdapter = new BookList(UserSearchBookActivity.this, books);
                            //attaching adapter to the listview
                            listViewBook.setAdapter(bookAdapter);

                            Toast.makeText(UserSearchBookActivity.this, "Searching for " + SearchBook, Toast.LENGTH_SHORT).show();
                            ETSearchBook.getText().clear();

                        } else {
                            Toast.makeText(UserSearchBookActivity.this, "Please enter book detail!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void showEditDeleteDialog(String bookId, String bookTitle, String bookWriter, String bookPublisher, String bookGenre, int bookPage, int bookAvailability) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_book_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText ETEditBookTitle = (EditText) dialogView.findViewById(R.id.ETEditBookTitle);
        final EditText ETEditBookWriter = (EditText) dialogView.findViewById(R.id.ETEditBookWriter);
        final EditText ETEditBookPublisher = (EditText) dialogView.findViewById(R.id.ETEditBookPublisher);
        final EditText ETEditBookGenre = (EditText) dialogView.findViewById(R.id.ETEditBookGenre);
        final EditText ETEditBookPage = (EditText) dialogView.findViewById(R.id.ETEditBookPage);
        final EditText ETEditAvailability = (EditText) dialogView.findViewById(R.id.ETEditAvailability);
        final Button BtnEditBook = (Button) dialogView.findViewById(R.id.BtnEditBook);
        final Button BtnDeleteBook = (Button) dialogView.findViewById(R.id.BtnDeleteBook);

        ETEditBookTitle.setText(bookTitle);
        ETEditBookWriter.setText(bookWriter);
        ETEditBookPublisher.setText(bookPublisher);
        ETEditBookGenre.setText(bookGenre);
        ETEditBookPage.setText(String.valueOf(bookPage));
        ETEditAvailability.setText(String.valueOf(bookAvailability));

        dialogBuilder.setTitle("Edit Book");
        final AlertDialog c = dialogBuilder.create();
        c.show();

        BtnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ETEditBookTitle.getText().toString().trim();
                String writer = ETEditBookWriter.getText().toString().trim();
                String publisher = ETEditBookPublisher.getText().toString().trim();
                String genre = ETEditBookGenre.getText().toString().trim();
                int page = Integer.parseInt(ETEditBookPage.getText().toString().trim());
                int availability = Integer.parseInt(ETEditAvailability.getText().toString().trim());

                if(TextUtils.isEmpty(title)){
                    Toast.makeText(getApplicationContext(),"Please insert book title",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(writer)){
                    Toast.makeText(getApplicationContext(),"Please insert writer name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(publisher)){
                    Toast.makeText(getApplicationContext(),"Please insert publisher name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(genre)){
                    Toast.makeText(getApplicationContext(),"Please insert book genre",Toast.LENGTH_LONG).show();
                    return;
                }
                if(page == 0){
                    Toast.makeText(getApplicationContext(),"Please insert book page",Toast.LENGTH_LONG).show();
                    return;
                }
                if(availability == 0){
                    Toast.makeText(getApplicationContext(),"Please insert book availability",Toast.LENGTH_LONG).show();
                    return;
                }

                updateLetter(bookId, title, writer, publisher, genre, page, availability);
                c.dismiss();
            }
        });

        BtnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteLetter(bookId);
                c.dismiss();
            }
        });
    }

    private boolean updateLetter(String id, String title, String writer, String publisher, String genre, int page, int availability) {
        //getting the specified letter reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Book").child(id);

        //updating letter
        Book book = new Book(id, title, writer, publisher, genre, page, availability);
        dR.setValue(book);
        Toast.makeText(getApplicationContext(), "Book Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteLetter(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Book").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Book Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

}