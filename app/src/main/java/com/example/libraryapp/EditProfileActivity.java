package com.example.libraryapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ETRace;
    private Button buttonEdit, buttonCancel;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SelectUser.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = firebaseAuth.getCurrentUser().getUid();

        ETRace = (EditText) findViewById(R.id.RaceProfile);
        radioGroup = findViewById(R.id.radiogp);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        buttonEdit.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonEdit){
            editUser();
        }

        if(v == buttonCancel){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }

    }

    private void editUser() {

        String race = ETRace.getText().toString().trim();
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        String gender = radioButton.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(race)){
            Toast.makeText(this,"Please enter your race",Toast.LENGTH_LONG).show();
            return;
        }

        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    String fullname = documentSnapshot.getString("full_name");
                    String matric = documentSnapshot.getString("card_number");
                    String email = documentSnapshot.getString("email");
                    String phone = documentSnapshot.getString("phone_number");
                    Map<String,Object> user = new HashMap<>();
                    user.put("full_name", fullname);
                    user.put("card_number", matric);
                    user.put("email", email);
                    user.put("phone_number",phone);
                    user.put("race",race);
                    user.put("gender",gender);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user Profile is edited for "+ userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
        finish();
        Toast.makeText(EditProfileActivity.this, "Edit successfullly!", Toast.LENGTH_LONG).show();
    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this,  String.valueOf(radioButton.getText()) + " selected",
                Toast.LENGTH_SHORT).show();
    }

}