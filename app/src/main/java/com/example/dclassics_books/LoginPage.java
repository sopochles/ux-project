package com.example.dclassics_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_USERNAME = "extra_username";
    EditText usn, pw;
    TextView error;
    Button login_btn;

    public void init() {
        usn = findViewById(R.id.usn_tf);
        pw = findViewById(R.id.pw_tf);
        error = findViewById(R.id.error_lbl);
        login_btn = findViewById(R.id.login_btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        init();
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == login_btn) {
            String username = usn.getText().toString().trim();
            String password = pw.getText().toString().trim();

            if(username.isEmpty()) {
                error.setText("Username must be filled");
            }
            else if(password.isEmpty()) {
                error.setText("Password must be filled");
            }
            else {
                boolean isValid = password.matches("^[a-zA-Z0-9]+$");
                boolean hasLetter = password.matches(".*[a-zA-Z].*");
                boolean hasDigit = password.matches(".*[0-9].*");

                if (!isValid) {
                    error.setText("Password must be alphanumeric");
                } else if (!hasLetter || !hasDigit) {
                    error.setText("Password must be alphanumeric");
                } else {
                    error.setText("");

                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                    intent.putExtra(EXTRA_USERNAME, username);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}