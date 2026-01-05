package com.example.dclassics_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class BookDetailPage extends AppCompatActivity {

    ImageView cover, back_btn;
    TextView title, author;
    EditText adr, phone;
    Button submit_btn;

    public void init() {
        cover = findViewById(R.id.book_cover);
        title = findViewById(R.id.book_title);
        author = findViewById(R.id.book_author);
        adr = findViewById(R.id.address_tf);
        phone = findViewById(R.id.phone_tf);
        submit_btn = findViewById(R.id.submit_btn);
        back_btn = findViewById(R.id.back_btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_page);

        init();
        submit_btn.setOnClickListener(v -> {
            String address = adr.getText().toString().trim();
            String phone_num = phone.getText().toString().trim();

            if(address.isEmpty() || phone_num.isEmpty()) {
                Toast.makeText(this, "Address and phone number must be filled", Toast.LENGTH_SHORT).show();
            } else {
                if (!phone_num.matches("\\d+")) {
                    Toast.makeText(this, "Phone number must be numeric", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Book submitted", Toast.LENGTH_LONG).show();
                }
            }
        });

        back_btn.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("BOOK_OBJECT")) {
            Book selectedBook = (Book) intent.getSerializableExtra("BOOK_OBJECT");

            if (selectedBook != null) {
                title.setText(selectedBook.getTitle());
                author.setText(selectedBook.getAuthor());
                cover.setImageResource(selectedBook.getCover());
            }
        }
    }

}