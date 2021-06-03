package com.example.gymapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.SystemParameterRule;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class tabnewex extends Fragment {

    private EditText exname;
    private EditText date;
    private EditText weight;
    private EditText reps;
    private Button saveBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exRef = db.collection("users").document("6ulCSe9sQpWhaHQy6xuP").collection("ex1");



    public tabnewex() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabnewex, container, false);

        exname = view.findViewById(R.id.textExercise);
        date = view.findViewById(R.id.textDate);
        weight = view.findViewById(R.id.textWeight);
        reps = view.findViewById(R.id.textReps);
        saveBtn = view.findViewById(R.id.saveBtn);

       // String exname1 = exname.getText().toString();
       // System.out.println("this is it: " + exname1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> exercise = new HashMap<>();
                exercise.put("exname", exname.getText().toString());
                exercise.put("weight", weight.getText().toString());
                exercise.put("reps", reps.getText().toString());
                exercise.put("date", date.getText().toString());

               System.out.println("this is it here: " + exname.getText().toString());


                exRef.document().set(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });



        return view;
    }



}
