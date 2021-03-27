package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class bookClasses extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference classRef = db.collection("classes");
    ArrayList<classData> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_classes);








        loadAll();

    }

    public void loadAll(){
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
                            //lop@lopSystem.out.println(data);

                            classList.add(classData);
                        }
                        // test if array list is being populated correct
                        System.out.println(classList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}
