package com.auroratan.covider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button btn_lregister, btn_llogin;
    EditText et_lemail, et_lpassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_lemail = (EditText) findViewById(R.id.et_lemail);
        et_lpassword = (EditText) findViewById(R.id.et_lpassword);
        btn_llogin = (Button) findViewById(R.id.btn_llogin);
        btn_lregister = (Button) findViewById(R.id.btn_lregister);

        btn_llogin.setOnClickListener(view -> {
            String email = et_lemail.getText().toString().trim();
            String password = et_lpassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                et_lemail.setError("USC email is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                et_lpassword.setError("Password is required");
                return;
            }

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    String email1 = et_lemail.getText().toString().trim();
                    i.putExtra("email", email1);
                    startActivity(i);
                } else {
                    Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}