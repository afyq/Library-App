package com.example.libraryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IssueBookActivity extends AppCompatActivity {

    DatabaseReference databaseBook;
    DatabaseReference databaseIssue;

    EditText ETSearchBook;
    Button BtnSearchBook;

    ListView listViewBook;

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);

        //getting the reference of book node
        databaseBook = FirebaseDatabase.getInstance().getReference("Book");
        databaseIssue = FirebaseDatabase.getInstance().getReference("Issue");

        //getting listviews
        listViewBook = (ListView) findViewById(R.id.listViewBook);

        ETSearchBook = findViewById(R.id.ETSearchBook);
        BtnSearchBook = (Button) findViewById(R.id.BtnSearchBook);

        //list to store letters
        books = new ArrayList<>();

        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = books.get(i);
                showEditDeleteDialog(book.getBookId(), book.getBookTitle(), book.getBookWriter(), book.getBookPublisher(), book.getBookGenre(), book.getBookPage(), book.getBookAvailability());
                return;
            }
        });

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

                        //clearing the previous artist list
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
                            BookList bookAdapter = new BookList(IssueBookActivity.this, books);
                            //attaching adapter to the listview
                            listViewBook.setAdapter(bookAdapter);

                            Toast.makeText(IssueBookActivity.this, "Searching for " + SearchBook, Toast.LENGTH_SHORT).show();
                            ETSearchBook.getText().clear();

                        } else {
                            Toast.makeText(IssueBookActivity.this, "Please enter book detail!", Toast.LENGTH_SHORT).show();
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
        final View dialogView = inflater.inflate(R.layout.issue_book_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView TVUnavailable = (TextView) dialogView.findViewById(R.id.TVUnavailable);
        final EditText ETIssueStudent = (EditText) dialogView.findViewById(R.id.ETIssueStudent);
        final Button BtnIssueBook = (Button) dialogView.findViewById(R.id.BtnIssueBook);

        TVUnavailable.setVisibility(View.GONE);
        ETIssueStudent.setVisibility(View.GONE);
        BtnIssueBook.setVisibility(View.GONE);

        if (bookAvailability == 0){
            TVUnavailable.setVisibility(View.VISIBLE);
            dialogBuilder.setTitle("Issue Book");
            final AlertDialog c = dialogBuilder.create();
            c.show();
        }else{

            ETIssueStudent.setVisibility(View.VISIBLE);
            BtnIssueBook.setVisibility(View.VISIBLE);

            dialogBuilder.setTitle("Issue Book");
            final AlertDialog c = dialogBuilder.create();
            c.show();

            //getting current date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy, H:mm a");

            BtnIssueBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = databaseIssue.push().getKey();
                    String name = ETIssueStudent.getText().toString().trim();
                    String issueDate = df.format(calendar.getTime());
                    String returnDate = null;
                    int availability = bookAvailability - 1;
                    String status = "on going";

                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getApplicationContext(), "Please insert student name!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Issue issue = new Issue(id, name, bookId, bookTitle, issueDate, returnDate, status);
                    databaseIssue.child(id).setValue(issue);

                    //updating book
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Book").child(bookId);
                    Book book = new Book(bookId, bookTitle, bookWriter, bookPublisher, bookGenre, bookPage, availability);
                    dR.setValue(book);

                    c.dismiss();

                    Toast.makeText(IssueBookActivity.this, "Book Issued", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

}