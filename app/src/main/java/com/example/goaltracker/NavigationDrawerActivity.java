package com.example.goaltracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

/*
This class is created by Yatri Patel
*/
public class NavigationDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imgProfile;
    private TextView userName, userEmailId;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // User is not logged in, redirect to login activity
            Intent intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is logged in, retrieve login credentials from SharedPreferences
            String name = sharedPreferences.getString("name", "");
            String email = sharedPreferences.getString("email", "");
            String password = sharedPreferences.getString("password", "");


            Toolbar toolbar = findViewById(R.id.toolbar);
            NavigationView navigationView = findViewById(R.id.nav_view);
            View headerview = navigationView.getHeaderView(0);
            imgProfile = (ImageView) headerview.findViewById(R.id.profile_imageView);
            userName = (TextView) headerview.findViewById(R.id.user_name);
            userEmailId = (TextView) headerview.findViewById(R.id.user_email_id);

            // Display User Details
            userName.setText(name);
            userEmailId.setText(email);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(

                    R.id.nav_home, R.id.nav_goal_monitoring, R.id.nav_add_goals, R.id.nav_contact_us, R.id.nav_setting)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            BottomNavigationView bottomView = findViewById(R.id.bottom_nav_view);
            NavigationUI.setupWithNavController(bottomView, navController);
            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(NavigationDrawerActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
            });

//            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
//            String savedEmail = sharedPreferences.getString("email", "");
//            String savedPassword = sharedPreferences.getString("password", "");
//
//            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//
//            if (!isLoggedIn) {
////             User is not logged in, redirect to login activity
//                Intent intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            } else {
//                // User is logged in, continue with the main activity
//                // ...
//            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}