  package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instaclone.Fragment.HomeFragment;
import com.example.instaclone.Fragment.NotificationFragment;
import com.example.instaclone.Fragment.ProfileFragment;
import com.example.instaclone.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

  public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Fragment selectFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){

                        case R.id.nav_home: {
                            selectFragment = new HomeFragment();
                            break;
                        }

                        case R.id.nav_heart: {
                            selectFragment = new NotificationFragment();
                            break;
                        }


                        case R.id.nav_profile: {
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectFragment = new ProfileFragment();
                            break;
                        }


                        case R.id.nav_search: {
                            selectFragment = new SearchFragment();
                            break;
                        }


                        case R.id.nav_add: {
                            selectFragment = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;
                        }
                    }

                    if(selectFragment!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
                    }
                      return true;
                }
            };
}
