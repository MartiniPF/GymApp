package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Training extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem tabworkout, tabnewex;
    private ViewPager viewPager;
    public PagerAdapter pagerAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exRef = db.collection("users").document("6ulCSe9sQpWhaHQy6xuP").collection("ex1");

    private EditText exname;
    private EditText date;
    private EditText weight;
    private EditText reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        tabLayout = findViewById(R.id.tablayout);
        tabworkout = findViewById(R.id.tabworkout);
        tabnewex = findViewById(R.id.tabnewex);
        viewPager = findViewById(R.id.viewpager);

        exname = findViewById(R.id.textExercise);
        date = findViewById(R.id.textDate);
        weight = findViewById(R.id.textWeight);
        reps = findViewById(R.id.textReps);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        final String email = getIntent().getStringExtra("email");


        // bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.botNav);
        bottomNavigationView.setSelectedItemId(R.id.item3); // create onResume method and copy this line of code to it (stops wrong selected item bug when user uses phones back button)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item1:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;

                    // other cases
                    case R.id.item2:
                        startActivity(new Intent(getApplicationContext(), bookClasses.class));
                        return true;

                    case R.id.item3:
                        //startActivity(new Intent(getApplicationContext(), Training.class));
                        return true;

                }
                return false;
            }
        });

    }


}
