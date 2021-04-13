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
    Button room1,room2,room3,room4,room5,room6,room7,room8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //hide actionbar
        getSupportActionBar().hide();

        anhxa();

        room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                startActivity(intent_r1);

            }
        });

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
    private void anhxa(){
        room1=(Button)findViewById(R.id.r1);
        room2=(Button)findViewById(R.id.r2);
        room3=(Button)findViewById(R.id.r3);
        room4=(Button)findViewById(R.id.r4);
        room5=(Button)findViewById(R.id.r5);
        room6=(Button)findViewById(R.id.r6);
        room7=(Button)findViewById(R.id.r7);
        room8=(Button)findViewById(R.id.r8);
        logout=(Button)findViewById(R.id.btn_log_out);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}