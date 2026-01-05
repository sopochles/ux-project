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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class BooksPage extends AppCompatActivity implements BooksAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    List<Book> allBookList = new ArrayList<>();
    RecyclerView booksRecyclerView;
    BooksAdapter booksAdapter;
    MaterialButtonToggleGroup toggleButtonGroupGenre;
    String currentFilterType = "Fiction";
    ImageView menu;
    DrawerLayout drawerLayout;
    NavigationView nav;
    String currentUsername;

    public void init() {
        booksRecyclerView = findViewById(R.id.books_recycler);
        toggleButtonGroupGenre = findViewById(R.id.toggle_button_group_genre);
        menu = findViewById(R.id.menu_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
    }

    public void loadAllBooks() {
        allBookList.clear();
        allBookList.add(new Book("Kagurabachi 01", "Takeru Hokazano", R.drawable.fiction_1, "Fiction"));
        allBookList.add(new Book("Fate/Strange Fake 1", "RYOHGO NARITA & SIDUKI MORII", R.drawable.fiction_2, "Fiction"));
        allBookList.add(new Book("Gokurakugai Vol.1", "Yuto Sano", R.drawable.fiction_3, "Fiction"));
        allBookList.add(new Book("Scrambled Vol. 1", "ROSALINA LINTANG", R.drawable.fiction_4, "Fiction"));
        allBookList.add(new Book("Record of Ragnarok 11", "AJICHIKA,SHINYA UMEMURA", R.drawable.fiction_5, "Fiction"));
        allBookList.add(new Book("Slam Dunk 14", "Inoue Takehiko", R.drawable.fiction_6, "Fiction"));
        allBookList.add(new Book("Weathering With You", "Makoto Shinkai", R.drawable.fiction_7, "Fiction"));
        allBookList.add(new Book("Sakamoto Days 15", "Yuto Suzuki", R.drawable.fiction_8, "Fiction"));

        allBookList.add(new Book("Why? People - Nelson Mandela", "Chanseok SEO", R.drawable.nonfiction_1, "Non-Fiction"));
        allBookList.add(new Book("Little People Big Dreams: Albert Einstein", "Maria Isabel Sanchez Vegara", R.drawable.nonfiction_2, "Non-Fiction"));
        allBookList.add(new Book("DI ANTARA 1000 NAPAS DAN KEHIDUPAN", "Elisa Herman", R.drawable.nonfiction_3, "Non-Fiction"));
        allBookList.add(new Book("WE'RE FAMILY BUT WE'RE STRANGERS", "Won Jung Mee", R.drawable.nonfiction_4, "Non-Fiction"));
        allBookList.add(new Book("SEGELAS KOPI BUAT PAK GURU", "SYABARRUDDIN", R.drawable.nonfiction_5, "Non-Fiction"));
        allBookList.add(new Book("Tetap Waras Di Tengah Orang Toksik", "Dr. Tim Cantopher", R.drawable.nonfiction_6, "Non-Fiction"));
        allBookList.add(new Book("NASGOR MAKANAN SEJUTA MAMAT", "KANG HARNAZ", R.drawable.nonfiction_7, "Non-Fiction"));
        allBookList.add(new Book("APA NAMANYA? KAMUS PERGAULAN ANAK", "PARK SUNG-WOO,KIM HYO-EUN", R.drawable.nonfiction_8, "Non-Fiction"));
    }

    public void applyFilter() {
        List<Book> filteredBooks = new ArrayList<>();
        if (allBookList != null) {
            for (Book book : allBookList) {
                if (book.getType() != null && book.getType().equalsIgnoreCase(currentFilterType)) {
                    filteredBooks.add(book);
                }
            }
        }
        if (booksAdapter != null) {
            booksAdapter.filterList(filteredBooks);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        init();
        loadAllBooks();

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

        booksAdapter = new BooksAdapter(new ArrayList<>(), this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        booksRecyclerView.setLayoutManager(layoutManager);
        booksRecyclerView.setAdapter(booksAdapter);


        toggleButtonGroupGenre.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.fiction_btn) {
                    currentFilterType = "Fiction";
                } else if (checkedId == R.id.nonfiction_btn) {
                    currentFilterType = "Non-Fiction";
                }
                applyFilter();
            }
        });

        if (toggleButtonGroupGenre.getCheckedButtonId() == R.id.nonfiction_btn) {
            currentFilterType = "Non-Fiction";
        } else {
            currentFilterType = "Fiction";
        }
        applyFilter();
    }

    @Override
    public void onItemClick(Book book) {
        Intent intent = new Intent(BooksPage.this, BookDetailPage.class);
        intent.putExtra("BOOK_OBJECT", book);

        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_books) {
        } else if (itemId == R.id.menu_home) {
            Intent homeIntent = new Intent(BooksPage.this, HomePage.class);
            homeIntent.putExtra(LoginPage.EXTRA_USERNAME, currentUsername);
            startActivity(homeIntent);
        } else if (itemId == R.id.menu_stores) {
            Intent storesIntent = new Intent(BooksPage.this, StoresPage.class);
            storesIntent.putExtra(LoginPage.EXTRA_USERNAME, currentUsername);
            startActivity(storesIntent);
        } else if (itemId == R.id.menu_logout) {
            Intent logoutIntent = new Intent(BooksPage.this, LoginPage.class);

            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(logoutIntent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}