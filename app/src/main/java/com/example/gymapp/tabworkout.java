package com.example.gymapp;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;
import static com.example.gymapp.Login.SHARED_PREFS;


/**
 * A simple {@link Fragment} subclass.
 */
public class tabworkout extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exRef = db.collection("users");
    private RecyclerView mRecyclerView;
    private workoutAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<exerciseData> exerciseList = new ArrayList<>();




    public tabworkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabworkout, container, false);
        mRecyclerView = view.findViewById(R.id.workoutList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());




        loadQuery("03/06/2021");








        return view;
    }


    public void loadQuery(String s){

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String texty = sharedPreferences.getString("emailID",""); // default value MUST be blank incase user is starting app for first time
        System.out.println("SHARED PREFS frag: " + texty);

        exRef.document(texty).collection("ex1").whereEqualTo("date", s).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){

                    System.out.println("CHECK THIS: " + documentSnapshot.getData());

                     exerciseData ed = documentSnapshot.toObject(exerciseData.class);
                     String exname = ed.getExname();
                     String date = ed.getDate();
                     int weight = ed.getWeight();
                     int reps = ed.getReps();

                     exerciseList.add(new exerciseData(exname, date, weight, reps));
                     System.out.println("THIS IS ARRAY: " + exerciseList);

                }

                mAdapter = new workoutAdapter(exerciseList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new workoutAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        System.out.println("Position: " + position);
                        //dataToString(position);

                    }
                });


            }
        });
    }

}
