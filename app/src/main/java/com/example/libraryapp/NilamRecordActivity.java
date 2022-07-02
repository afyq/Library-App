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

public class NilamRecordActivity extends AppCompatActivity {

    DatabaseReference databaseNilam;

    EditText ETSearchNilam;
    Button BtnSearchNilam;

    ListView listViewNilam;

    List<Nilam> nilams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilam_record);

        //getting the reference of letters node
        databaseNilam = FirebaseDatabase.getInstance().getReference("Nilam");

        //getting listviews
        listViewNilam = (ListView) findViewById(R.id.listViewBook);

        ETSearchNilam = findViewById(R.id.ETSearchBook);
        BtnSearchNilam = (Button) findViewById(R.id.BtnSearchBook);

        //list to store letters
        nilams = new ArrayList<>();

        listViewNilam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Nilam nilam = nilams.get(i);
                showEditDeleteDialog(nilam.getBookId(), nilam.getBookTitle(), nilam.getBookWriter(), nilam.getBookPublisher(), nilam.getBookLanguage(), nilam.getBookYearPublish(), nilam.getBookToRM());
                return;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        BtnSearchNilam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attaching value event listener
                databaseNilam.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String SearchNilam = ETSearchNilam.getText().toString().trim();

                        //clearing the previous list
                        nilams.clear();

                        if (!TextUtils.isEmpty(SearchNilam)) {
                            //iterating through all the nodes
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //getting letter
                                Nilam nilam = postSnapshot.getValue(Nilam.class);

                                if ((nilam.getBookTitle() != null && nilam.getBookTitle().toLowerCase().contains(SearchNilam.toLowerCase()))
                                        || (nilam.getBookWriter() != null && nilam.getBookWriter().toLowerCase().contains(SearchNilam.toLowerCase()))
                                        || (nilam.getBookPublisher() != null && nilam.getBookPublisher().toLowerCase().contains(SearchNilam.toLowerCase()))
                                        || (nilam.getBookLanguage() != null && nilam.getBookLanguage().toLowerCase().contains(SearchNilam.toLowerCase()))) {
                                    //adding letter to the list
                                    nilams.add(nilam);
                                }
                            }

                            //creating adapter
                            NilamList nilamAdapter = new NilamList(NilamRecordActivity.this, nilams);
                            //attaching adapter to the listview
                            listViewNilam.setAdapter(nilamAdapter);

                            Toast.makeText(NilamRecordActivity.this, "Searching for " + SearchNilam, Toast.LENGTH_SHORT).show();
                            ETSearchNilam.getText().clear();

                        } else {
                            Toast.makeText(NilamRecordActivity.this, "Please enter book detail!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void showEditDeleteDialog(String bookId, String bookTitle, String bookWriter, String bookPublisher, String bookLanguage, int bookYearPublish, String bookToRM) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_nilam_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText ETEditBookTitle = (EditText) dialogView.findViewById(R.id.ETEditBookTitle);
        final EditText ETEditBookWriter = (EditText) dialogView.findViewById(R.id.ETEditBookWriter);
        final EditText ETEditBookPublisher = (EditText) dialogView.findViewById(R.id.ETEditBookPublisher);
        final EditText ETEditBookLanguage = (EditText) dialogView.findViewById(R.id.ETEditBookLanguage);
        final EditText ETEditBookYearPublish = (EditText) dialogView.findViewById(R.id.ETEditBookYearPublish);
        final EditText ETEditToRM = (EditText) dialogView.findViewById(R.id.ETEditToRM);
        final Button BtnEditNilam = (Button) dialogView.findViewById(R.id.BtnEditNilam);
        final Button BtnDeleteNilam = (Button) dialogView.findViewById(R.id.BtnDeleteNilam);

        ETEditBookTitle.setText(bookTitle);
        ETEditBookWriter.setText(bookWriter);
        ETEditBookPublisher.setText(bookPublisher);
        ETEditBookLanguage.setText(bookLanguage);
        ETEditBookYearPublish.setText(String.valueOf(bookYearPublish));
        ETEditToRM.setText(bookToRM);

        dialogBuilder.setTitle("Edit Nilam");
        final AlertDialog c = dialogBuilder.create();
        c.show();

        BtnEditNilam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ETEditBookTitle.getText().toString().trim();
                String writer = ETEditBookWriter.getText().toString().trim();
                String publisher = ETEditBookPublisher.getText().toString().trim();
                String language = ETEditBookLanguage.getText().toString().trim();
                int year = Integer.parseInt(ETEditBookYearPublish.getText().toString().trim());
                String torm = ETEditToRM.getText().toString().trim();

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
                if(TextUtils.isEmpty(language)){
                    Toast.makeText(getApplicationContext(),"Please insert book language",Toast.LENGTH_LONG).show();
                    return;
                }
                if(year == 0){
                    Toast.makeText(getApplicationContext(),"Please insert book year publish",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(torm)){
                    Toast.makeText(getApplicationContext(),"Please insert type of reading material",Toast.LENGTH_LONG).show();
                    return;
                }

                updateLetter(bookId, title, writer, publisher, language, year, torm);
                c.dismiss();
            }
        });

        BtnDeleteNilam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteLetter(bookId);
                c.dismiss();
            }
        });
    }

    private boolean updateLetter(String id, String title, String writer, String publisher, String language, int year, String torm) {
        //getting the specified letter reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Nilam").child(id);

        //updating letter
        Nilam nilam = new Nilam(id, title, writer, publisher, language, year, torm);
        dR.setValue(nilam);
        Toast.makeText(getApplicationContext(), "Nilam Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteLetter(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Nilam").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Nilam Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

}