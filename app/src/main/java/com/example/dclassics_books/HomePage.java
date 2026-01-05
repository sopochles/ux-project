package com.example.dclassics_books;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView usn;
    List<Book> bookList = new ArrayList<>();
    RecyclerView featuredBooksRecyclerView;
    FeaturedAdapter featuredAdapter;
    List<Integer> storeList = new ArrayList<>();
    ViewPager2 storesCarousel;
    StoreCarouselAdapter carouselAdapter;
    Handler sliderHandler = new Handler();
    Runnable sliderRunnable;
    ImageView left_btn, right_btn, menu;
    DrawerLayout drawerLayout;
    NavigationView nav;

    String currentUsername;

    public void init() {
        usn = findViewById(R.id.usn_lbl);
        featuredBooksRecyclerView = findViewById(R.id.featured_recycler);
        storesCarousel = findViewById(R.id.stores_carousel);
        left_btn = findViewById(R.id.left_btn);
        right_btn = findViewById(R.id.right_btn);
        menu = findViewById(R.id.menu_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
    }

    public void featured() {
        bookList.add(new Book("Sebuah Seni Untuk Bersikap Bodo Amat", "Mark Manson", R.drawable.featured_1, "Fiction"));
        bookList.add(new Book("Fate/Strange Fake 1", "RYOHGO NARITA & SIDUKI MORII", R.drawable.featured_2, "Fiction"));
        bookList.add(new Book("Gokurakugai Vol.1", "Yuno Sato", R.drawable.featured_3, "Fiction"));
        bookList.add(new Book("The Catcher in the Rye", "JD Salinger", R.drawable.featured_4, "Non-Fiction"));

        featuredAdapter = new FeaturedAdapter(bookList);
        featuredBooksRecyclerView.setAdapter(featuredAdapter);
    }

    public void stores() {
        storeList = new ArrayList<>();
        storeList.add(R.drawable.store_1);
        storeList.add(R.drawable.store_2);
        storeList.add(R.drawable.store_3);
        storeList.add(R.drawable.store_4);
    }

    public void carousel() {
        carouselAdapter = new StoreCarouselAdapter(storeList);
        storesCarousel.setAdapter(carouselAdapter);

        storesCarousel.post(() -> {
            int startPosition = storeList.size() * 1000;
            storesCarousel.setCurrentItem(startPosition, false);
        });

        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                storesCarousel.setCurrentItem(storesCarousel.getCurrentItem() + 1, true);
                sliderHandler.postDelayed(this, 5000);
            }
        };

        right_btn.setOnClickListener(v -> {
            sliderHandler.removeCallbacks(sliderRunnable);
            storesCarousel.setCurrentItem(storesCarousel.getCurrentItem() + 1, true);
            sliderHandler.postDelayed(sliderRunnable, 5000);
        });

        left_btn.setOnClickListener(v -> {
            sliderHandler.removeCallbacks(sliderRunnable);
            storesCarousel.setCurrentItem(storesCarousel.getCurrentItem() - 1, true);
            sliderHandler.postDelayed(sliderRunnable, 5000);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        featured();
        stores();
        carousel();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(LoginPage.EXTRA_USERNAME)) {
            String username = intent.getStringExtra(LoginPage.EXTRA_USERNAME);
            if (username != null && !username.isEmpty()) {
                currentUsername = username;
                usn.setText(username);

                View headerView = nav.getHeaderView(0);
                TextView nav_usn = headerView.findViewById(R.id.nav_usn_lbl);
                nav_usn.setText(username);
            }
        }

        nav.setNavigationItemSelectedListener(this);
        menu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_home) {
        } else if (itemId == R.id.menu_books) {
            Intent booksIntent = new Intent(HomePage.this, BooksPage.class);
            booksIntent.putExtra(LoginPage.EXTRA_USERNAME, currentUsername);
            startActivity(booksIntent);
        } else if (itemId == R.id.menu_stores) {
            Intent storesIntent = new Intent(HomePage.this, StoresPage.class);
            storesIntent.putExtra(LoginPage.EXTRA_USERNAME, currentUsername);
            startActivity(storesIntent);
        } else if (itemId == R.id.menu_logout) {
            Intent logoutIntent = new Intent(HomePage.this, LoginPage.class);

            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(logoutIntent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}