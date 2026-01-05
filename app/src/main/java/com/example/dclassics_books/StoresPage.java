package com.example.dclassics_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class StoresPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Store> storesList = new ArrayList<>();
    RecyclerView storesRecyclerView;
    StoresAdapter storesAdapter = new StoresAdapter(storesList);
    ImageView menu;
    DrawerLayout drawerLayout;
    NavigationView nav;
    String currentUsername;

    public void init() {
        storesRecyclerView = findViewById(R.id.stores_recycler);
        menu = findViewById(R.id.menu_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
    }

    public void stores() {
        storesList.add(new Store("Kinokuniya", "Sān-Z STUDIO, Lumina Square", R.drawable.store_1));
        storesList.add(new Store("Gramedia", "Luminous Hall, Starloop", R.drawable.store_2));
        storesList.add(new Store("Periplus", "Bardic Needle, Sixth Street", R.drawable.store_3));
        storesList.add(new Store("Books & Beyond", "Ballet Twins Road, Ballet Twins", R.drawable.store_4));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        init();
        stores();

        storesRecyclerView.setAdapter(storesAdapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(LoginPage.EXTRA_USERNAME)) {
            String username = intent.getStringExtra(LoginPage.EXTRA_USERNAME);
            if (username != null && !username.isEmpty()) {
                currentUsername = username;

                View headerView = nav.getHeaderView(0);
                TextView nav_usn = headerView.findViewById(R.id.nav_usn_lbl);
                nav_usn.setText(username);
            }
        }

        nav.setNavigationItemSelectedListener(this);
        menu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_stores) {
        } else if (itemId == R.id.menu_home) {
            Intent homeIntent = new Intent(StoresPage.this, HomePage.class);
            homeIntent.putExtra(LoginPage.EXTRA_USERNAME, currentUsername);
            startActivity(homeIntent);
        } else if (itemId == R.id.menu_books) {
            Intent booksIntent = new Intent(StoresPage.this, BooksPage.class);
            booksIntent.putExtra(LoginPage.EXTRA_USERNAME, currentUsername);
            startActivity(booksIntent);
        } else if (itemId == R.id.menu_logout) {
            Intent logoutIntent = new Intent(StoresPage.this, LoginPage.class);

            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(logoutIntent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}