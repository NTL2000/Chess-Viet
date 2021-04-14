package com.TLU.chessviet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;

public class Home extends AppCompatActivity {
    Button logout;
    FirebaseUser firebaseUser;
    Button room1,room2,room3,room4,room5,room6,room7,room8;
    private DatabaseReference mdata;
    private boolean label=true;
    private String id;
    public static String now_time;
    private long count;
    ImageView avatar;
    FirebaseUser user;
    TextView name;
    TextView score;
    private BroadcastReceiver receiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //data
        mdata= FirebaseDatabase.getInstance().getReference();
        Calendar calendar = Calendar.getInstance();
        long timenow = calendar.getTimeInMillis();
        now_time=timenow+"";

        //hide actionbar
        getSupportActionBar().hide();

        anhxa();
        //gán user
        user=FirebaseAuth.getInstance().getCurrentUser();
        Picasso.with(this).load(user.getPhotoUrl()).into(avatar);
        name.setText(user.getDisplayName());

//        broad cast receiver
        receiver=new BroadCastReceiver();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,intentFilter);




        room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=1;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=2;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=3;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=4;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=5;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=6;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=7;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


            }
        });
        room8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChessActivity.Room=8;
                mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).setValue("0000");

                mdata.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(now_time).removeValue();
                        count=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount();
                        if(count==2||snapshot.child("Room"+String.valueOf(ChessActivity.Room)).hasChild("Black")){
                            ChessActivity.white_board=true;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
                        }
                        else {
                            ChessActivity.white_board=false;
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count<=3){
                            Intent intent_r1=new Intent(Home.this,ChessActivity.class);
                            startActivity(intent_r1);
                        }
                        else {
                            Toast.makeText(Home.this,"Phòng đã đủ người chơi",Toast.LENGTH_SHORT).show();
                            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child(Home.now_time).removeValue();
                        }


                    }
                }, 1000);


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
        avatar=(ImageView)findViewById(R.id.avartar);
        name=(TextView)findViewById(R.id.name);
        score=(TextView)findViewById(R.id.score);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}