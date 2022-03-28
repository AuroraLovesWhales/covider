package com.auroratan.covider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText et_firstname, et_lastname, et_email, et_password;
    Button btn_register, btn_login;
    FirebaseAuth fAuth;
    //FirebaseFirestore fStore;
    DatabaseReference reference;
    String userID;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    int radioButtonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonID = radioGroup.getCheckedRadioButtonId();

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        //fStore = FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

        btn_register.setOnClickListener(view -> {
            String firstName = et_firstname.getText().toString().trim();
            String lastName = et_lastname.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            boolean isInstructor;

            if (TextUtils.isEmpty(email)) {
                et_email.setError("USC email is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                et_password.setError("Password is required");
                return;
            }
            if (radioButtonID == -1) {
                Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                return;
            } else {
                selectedRadioButton = findViewById(radioButtonID);
                String selectedRbText = selectedRadioButton.getText().toString();
                if (selectedRbText == "Instructor") {
                    isInstructor = false;
                } else {
                    isInstructor = true;
                }
            }

            // register user in firebase
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    User usr = new User(email, password, firstName, lastName, isInstructor);
                    FirebaseUser firebaseUser = fAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    Map<String, Object> newUser = new HashMap<>();
                    newUser.put("first name", usr.getFirstName());
                    newUser.put("last name", usr.getLastName());
                    newUser.put("email", usr.getEmail());
                    newUser.put("password", usr.getPassword());
                    newUser.put("isInstructor", usr.getIsInstructor());
                    reference.setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("onCreateUserLog", "onSuccess: user profile is created for " + userid);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("onCreateUserLog", "onFailure: " + e.toString());
                        }
                    });

//                    userID = fAuth.getCurrentUser().getUid();
//                    DocumentReference documentReference = fStore.collection("User Created").document(userID);
//                    Map<String, Object> newUser = new HashMap<>();
//                    newUser.put("first name", usr.getFirstName());
//                    newUser.put("last name", usr.getLastName());
//                    newUser.put("email", usr.getEmail());
//                    newUser.put("password", usr.getPassword());
//                    newUser.put("isInstructor", usr.getIsInstructor());
//
//                    documentReference.set(newUser).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
//                        Log.d("onCreateUserLog", "onSuccess: user profile is created for " + userID);
//                    }).addOnFailureListener(e -> Log.d("onCreateUserLog", "onFailure: " + e.toString()));

                    // after successful registration, direct page to profile (MainActivity)
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(i);

                } else {
                    Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

//    private boolean checkRadioButton(int radioButtonID) {
//        if (radioButtonID)
//
//
//        switch(radioButtonID) {
//            case R.id.radio_student: return false;
//            case R.id.radio_instructor: return true;
//        }
//    }
}