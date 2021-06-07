package com.example.gymapp;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
    private Button loadBtn;
    ArrayList<exerciseData> exerciseList = new ArrayList<>();
    String dateEntry;
    String daytext;
    String monthtext;
    String yeartext;




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
        loadBtn = view.findViewById(R.id.loadWorkoutBtn);







        // ----------------------------------- 3 DATE SELECTION SPINNERS ------------------------------------------------------------------------------
        Spinner spinnerDay = view.findViewById(R.id.daySpinner);
        Spinner spinnerMonth = view.findViewById(R.id.monthSpinner);
        Spinner spinnerYear = view.findViewById(R.id.yearSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.dayNos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daytext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.months, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter2);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String holder = parent.getItemAtPosition(position).toString();
                switch(holder){
                    case "Jan":
                        monthtext = "1";
                        break;

                    case "Feb":
                        monthtext = "2";
                        break;

                    case "March":
                        monthtext = "3";
                        break;

                    case "Apr":
                        monthtext = "4";
                        break;

                    case "May":
                        monthtext = "5";
                        break;

                    case "Jun":
                        monthtext = "6";
                        break;

                    case "Jul":
                        monthtext = "7";
                        break;

                    case "Aug":
                        monthtext = "8";
                        break;

                    case "Sep":
                        monthtext = "9";
                        break;

                    case "Oct":
                        monthtext = "10";
                        break;

                    case "Nov":
                        monthtext = "11";
                        break;

                    case "Dec":
                        monthtext = "12";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getActivity(), R.array.years, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter3);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yeartext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // ----------------------------------------------------------------------------------------------------------------------------------------

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseList.clear();
                dateEntry = daytext + "/" + monthtext + "/" + yeartext;
                System.out.println("completed datentry: " + dateEntry);
                loadQuery(dateEntry);
            }
        });



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
