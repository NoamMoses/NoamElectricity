package com.noam.noamelectricity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /*
    * This is the MainActivity AND NOT MainPage
    * This class is just responsible on handling the Drawer Activity side menu and top toolbar
    * All of the main page things, should be written in MainPage.java, and not here
    * */

    private DrawerLayout drawer;
    Toolbar toolbar; // Top toolbar
    int selectedItemIndex = 0; // Menu item index
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Adding the menu, and setting-up the drawer activity things - menu, top title (toolbar), etc
        * */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainPage()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /*
        * Handling the left open menu
        * */
        if (item.getItemId() == R.id.nav_home) {
            selectedItemIndex = 1;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainPage()).commit();
        } else if (item.getItemId() == R.id.nav_formulas) {
            selectedItemIndex = 2;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FormulasFragment()).commit();
        } else if (item.getItemId() == R.id.nav_laws) {
            selectedItemIndex = 3;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LawsFragment()).commit();
        } else if (item.getItemId() == R.id.nav_ohm) {
            selectedItemIndex = 4;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new OhmFragment()).commit();
        } else if (item.getItemId() == R.id.nav_resCalc) {
            selectedItemIndex = 5;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ResistanceFragment()).commit();
        } else if (item.getItemId() == R.id.nav_todo) {
            selectedItemIndex = 6;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TODOFragment()).commit();
        } else if (item.getItemId() == R.id.nav_about) {
            selectedItemIndex = 7;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AboutFragment()).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}