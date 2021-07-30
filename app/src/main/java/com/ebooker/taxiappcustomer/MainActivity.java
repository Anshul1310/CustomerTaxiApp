package com.ebooker.taxiappcustomer;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_theme));
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, null,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new HomeFragment())
                .commit();

        View nav_layout = navigationView.getChildAt(0);

        findViewById(R.id.nav_drawer_item_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(MainActivity.this, "hlo", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.nav_drawer_item_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProfileFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.nav_drawer_item_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new RideHistory()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.nav_drawer_item_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SupportFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

    }

    public void openNavDrawer(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}