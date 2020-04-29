  package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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

        if (Build.VERSION.SDK_INT >= 23) {
            isStoragePermissionGranted();
        }


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


      public  boolean isStoragePermissionGranted() {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                      == PackageManager.PERMISSION_GRANTED) {
                  //   Log.v(TAG,"Permission is granted");
                  return true;
              } else {

                  //  Log.v(TAG,"Permission is revoked");
                  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                  return false;
              }
          }
          else { //permission is automatically granted on sdk<23 upon installation
              //  Log.v(TAG,"Permission is granted");
              return true;
          }
      }

      @Override
      public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
              //   Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
              //resume tasks needing this permission
          }
      }
}
