package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    public static final String SHARED_PREFS = "sharedPrefs";

    private EditText editTextEmail;
    private EditText editTextPass;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");
    String emailEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.emailEntry);
        editTextPass = findViewById(R.id.passEntry);

    }

    // code currently made to register
    public void login(View v){
        emailEntry = editTextEmail.getText().toString();
        final String passEntry = editTextPass.getText().toString();
       // String pass = "";

        usersRef.whereEqualTo("email", emailEntry)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                           // String email = documentSnapshot.getString(KEY_EMAIL);
                            String pass = documentSnapshot.getString(KEY_PASS);

                            if(pass.equals(passEntry)){
                                Toast.makeText(Login.this, "Logging in success", Toast.LENGTH_SHORT).show();

                                Intent goMain = new Intent(getApplicationContext(), MainActivity.class);
                                goMain.putExtra("email", emailEntry);
                                startActivity(goMain);

                                SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("emailID", emailEntry);
                                editor.apply();




                            }
                            else {
                                Toast.makeText(Login.this, "No Bueno", Toast.LENGTH_SHORT).show();
                            }
                        }

                        System.out.println("DOC ID HERE: " + usersRef.document());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void logins(View v){
        // String email = editTextEmail.getText().toString();
        // String pass = editTextPass.getText().toString();

        db.collection("users").document("user123")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String email = documentSnapshot.getString(KEY_EMAIL);
                            String pass = documentSnapshot.getString(KEY_PASS);

                            System.out.println("Test values here: email =" + email + ", pass = " + pass);

                        }
                        else{
                            Toast.makeText(Login.this, "user dont exits", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void goToRegister(View view){
        Intent goReg = new Intent(getApplicationContext(), Register.class);
        startActivity(goReg);
    }

}
