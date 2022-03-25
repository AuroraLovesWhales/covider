package com.auroratan.covider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btn_logout;
    TextView welcome_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        welcome_message = (TextView) findViewById(R.id.welcome_message);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String emailValue = extras.getString("email");
            welcome_message.setText("Welcome! " + emailValue);
        }

        btn_logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut(); // logout
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });
    }
}