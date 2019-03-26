package com.example.dnelv.casino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.dnelv.casino.blackjack.Blackjack;
import com.example.dnelv.casino.home.HomeFragment;
import com.example.dnelv.casino.home.MyProfileFragment;
import com.example.dnelv.casino.home.SettingsFragment;
import com.example.dnelv.casino.login.LoginActivity;
import com.example.dnelv.casino.spilleautomat.Spilleautomat;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static TextView textSaldo;
    private static int saldo = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences prefs = getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        if (prefs.getString("Username", null) == null){
            Intent loggInnIntent = new Intent(this, LoginActivity.class);
            startActivity(loggInnIntent);
        }

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        textSaldo = headerView.findViewById(R.id.nav_header_saldo);
        textSaldo.setText("" + saldo);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void blackJack(View view) {
        Intent intent = new Intent(getBaseContext(), Blackjack.class);
        this.startActivity(intent);
    }

    public void spilleAutomat(View view) {
        Intent intent = new Intent(getBaseContext(), Spilleautomat.class);
        this.startActivity(intent);
    }

    public static void setSaldo(int nySaldo) {
        saldo = nySaldo;
    }

    public static int getSaldo() {
        return saldo;
    }

    public static void updateSaldo(int newSaldo) {
        saldo = newSaldo;
        textSaldo.setText(""+saldo);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_myProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyProfileFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
