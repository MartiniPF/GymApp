package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";

    private EditText editTextEmail;
    private EditText editTextPass;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.emailEntry2);
        editTextPass = findViewById(R.id.passEntry2);
    }

    public void register(View v){
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put(KEY_EMAIL, email);
        user.put(KEY_PASS, pass);



        usersRef.add(user);




    }
}
