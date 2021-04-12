package com.TLU.chessviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    Button logout;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide actionbar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);
        logout=(Button)findViewById(R.id.btn_log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (firebaseUser == null){
                    startActivity(new Intent(Home.this,Main.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}