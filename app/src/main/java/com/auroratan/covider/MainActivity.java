package com.auroratan.covider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    Button btn_logout;
    TextView tv_fullname, tv_email;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    //FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        //fStore = FirebaseFirestore.getInstance();
        //userID = fAuth.getCurrentUser().getUid();

        btn_logout = findViewById(R.id.btn_logout);
        tv_fullname = findViewById(R.id.tv_fullname);
        tv_email = findViewById(R.id.tv_email);

        if(fAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));
        }
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tv_fullname.setText("Welcome! " + user.getFirstName() + " " + user.getLastName());
                tv_email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        DocumentReference documentReference = fStore.collection("User Created").document(userID);
//        documentReference.addSnapshotListener(this, (documentSnapshot, error) -> {
//            String firstName = documentSnapshot.getString("first name");
//            String lastName = documentSnapshot.getString("last name");
//            tv_fullname.setText("Welcome! " + firstName + " " + lastName);
//            tv_email.setText(documentSnapshot.getString("email"));
//        });

        btn_logout.setOnClickListener(view -> {
            fAuth.signOut(); // logout
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        });
    }
}