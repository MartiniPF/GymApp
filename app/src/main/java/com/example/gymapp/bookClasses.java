package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class bookClasses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private RecyclerView mRecyclerView;
    private classesAdapter mAdapter;
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
        Spinner spinner = findViewById(R.id.spinnerClasses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classCats, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


      //  loadAll();

       // classList.add(new classData("line", "as","df","dff",12,1));
        //loadAll();

        System.out.println(classList);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
    }

    @Override // on spinner GymClass option chosen
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(parent.getContext(), "item chosen" , Toast.LENGTH_SHORT);

        String text = parent.getItemAtPosition(position).toString();
        System.out.println("Spinner activated: " + text);

        loaders(text);
    }

    public void loaders(String s){

        classList.clear();

        classRef.whereEqualTo("name", s).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
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
                mAdapter.setOnItemClickListener(new classesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);
                    }
                });
            }
        });
        System.out.println("array at end of on start method: " + classList);

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
                mAdapter.setOnItemClickListener(new classesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);
                    }
                });
            }
        });
        System.out.println("array at end of on start method: " + classList);
    }

    public void loadAll(View v){

        classList.clear();

        classRef.whereEqualTo("name", "running").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
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
                mAdapter.setOnItemClickListener(new classesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);
                    }
                });
            }
        });
        System.out.println("array at end of on start method: " + classList);



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }


}
