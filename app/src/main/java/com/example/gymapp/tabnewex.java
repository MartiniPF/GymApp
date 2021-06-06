package com.example.gymapp;


import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;
import static com.example.gymapp.Login.SHARED_PREFS;


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
    private CollectionReference exRef = db.collection("users");



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

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final String texty = sharedPreferences.getString("emailID",""); // default value MUST be blank incase user is starting app for first time
        System.out.println("SHARED PREFS frag: " + texty);

       // String exname1 = exname.getText().toString();
       // System.out.println("this is it: " + exname1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int repNo = Integer.parseInt(reps.getText().toString());
                int weightNo = Integer.parseInt(weight.getText().toString());

                Map<String, Object> exercise = new HashMap<>();
                exercise.put("exname", exname.getText().toString());
                exercise.put("weight", weightNo);
                exercise.put("reps", repNo);
                exercise.put("date", date.getText().toString());

               System.out.println("this is it here: " + exname.getText().toString());


                exRef.document(texty).collection("ex1").document().set(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
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
