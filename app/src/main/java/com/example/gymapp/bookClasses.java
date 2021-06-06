package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        // bottom menu bar stuff
        BottomNavigationView bottomNavigationView = findViewById(R.id.botNav);
        bottomNavigationView.setSelectedItemId(R.id.item2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item1:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;

                    // other cases
                    case R.id.item2:
                       // startActivity(new Intent(getApplicationContext(), bookClasses.class));
                        return true;

                    case R.id.item3:
                        startActivity(new Intent(getApplicationContext(), Training.class));
                        return true;

                }
                return false;
            }
        });
        //-----------------------

        testText = findViewById(R.id.testText);
        Spinner spinner = findViewById(R.id.spinnerClasses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classCats, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
    }

    @Override // on spinner GymClass option chosen
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(parent.getContext(), "item chosen" , Toast.LENGTH_SHORT);

        String text = parent.getItemAtPosition(position).toString();
        System.out.println("Spinner activated: " + text);

        testText.setText("");
        if(position > 0) {

            loadQuery(text);
        }
        else {

            System.out.println("show all");
            loadAll();
        }
    }

    public void loadQuery(String s){

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

                    classList.add(new classData(name, date, time, length, capacity, attendees));
                }

                mAdapter = new classesAdapter(classList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new classesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);
                        dataToString(position);



                    }
                });
            }
        });
    }

    public void dataToString(int position){

        String data = "";
        String name = classList.get(position).getName();
        String date = classList.get(position).getDate();
        String time = classList.get(position).getTime();
        String length = classList.get(position).getLength();
        int capacity = classList.get(position).getCapacity();
        int attendees = classList.get(position).getAttendees();

        data = "Name: " + name + "\nDate: " +  date + "\nTime " + time + "\nLength: " + length
                + "\nCapacity: " + capacity + "\nAttendees: " + attendees;

        testText.setText(data);
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
                }

                mAdapter = new classesAdapter(classList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new classesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);
                        dataToString(position);
                    }
                });
            }
        });
    }

    public void loadAll(){

        classList.clear();
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
                }

                mAdapter = new classesAdapter(classList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new classesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);

                        dataToString(position);
                    }
                });
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}
