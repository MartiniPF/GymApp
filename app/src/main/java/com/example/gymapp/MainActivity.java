package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.gymapp.Login.SHARED_PREFS;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        final String email = getIntent().getStringExtra("email");
        System.out.println("EMAIL INTENT: " + email);


        BottomNavigationView bottomNavigationView = findViewById(R.id.botNav);
        bottomNavigationView.setSelectedItemId(R.id.item1); // create onResume method and copy this line of code to it (stops wrong selected item bug when user uses phones back button)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item1:
                        //startActivity(new Intent(getApplicationContext(), bookClasses.class));
                        return true;

                    // other cases
                    case R.id.item2:
                        startActivity(new Intent(getApplicationContext(), bookClasses.class));

                        return true;

                    case R.id.item3:
                       // startActivity(new Intent(getApplicationContext(), Training.class));

                        Intent go3 = new Intent(getApplicationContext(), Training.class);
                        go3.putExtra("email", email);
                        startActivity(go3);
                        return true;

                }
                return false;
            }
        });

    }

    public void goToClasses(View view){
        Intent goClasses = new Intent(getApplicationContext(), bookClasses.class);
        startActivity(goClasses);
    }
}
