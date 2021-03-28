package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class bookClasses extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<classData> classList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference classRef = db.collection("classes");
    private TextView testText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_classes);
        testText = findViewById(R.id.testText);


      //  loadAll();

       // classList.add(new classData("line", "as","df","dff",12,1));
        //loadAll();

        System.out.println(classList);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);





    }

    @Override
    protected void onStart(){
        super.onStart();
        classRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    return;
                }

                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                    classData classData = documentSnapshot.toObject(com.example.gymapp.classData.class);

                    String name = classData.getName();
                    String date = classData.getDate();
                    String time = classData.getTime();
                    String length = classData.getLength();
                    int capacity = classData.getCapacity();
                    int attendees = classData.getAttendees();

                    data += "Name: " + name + "\nDate: " +  date + "\nTime " + time + "\nLength: " + length
                            + "\nCapacity: " + capacity + "\nAttendees: " + attendees;

                    //System.out.println(data);
                    classList.add(new classData(name, date, time, length, capacity, attendees));
                    testText.setText(data);

                }

                mAdapter = new classesAdapter(classList);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        System.out.println("array at end of on start method: " + classList);
    }

    public void loadAll(){
        classList = new ArrayList<>();

        classRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            classData classData = documentSnapshot.toObject(classData.class);

                            String name = classData.getName();
                            String date = classData.getDate();
                            String time = classData.getTime();
                            String length = classData.getLength();
                            int capacity = classData.getCapacity();
                            int attendees = classData.getAttendees();

                            data += "Name: " + name + "\nDate: " +  date + "\nTime " + time + "\nLength: " + length
                                    + "\nCapacity: " + capacity + "\nAttendees: " + attendees;

                            // test if data string populated properly
                            System.out.println(data);

                            classList.add(new classData(name, date, time, length, capacity, attendees));
                            System.out.println("array in for loop: " + classList);
                        }
                        // test if array list is being populated correct
                        System.out.println("array outside for loop: " + classList);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        System.out.println("array at end of load method: " + classList);
    }

}
