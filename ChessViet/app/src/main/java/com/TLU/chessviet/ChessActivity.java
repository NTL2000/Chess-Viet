package com.TLU.chessviet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserManager;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class ChessActivity extends AppCompatActivity {

    ChessBoard mChessBoard;
    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mFilter;


    public static boolean white_board;
    private DatabaseReference mdata;
    private boolean label=true;
    boolean move_able;
    int count=0;
    FirebaseUser user;
    public static  int Room;


    @Override
    protected void onRestart() {
        super.onRestart();
        label=false;
        if(white_board){
            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").setValue("0000");
        }
        else {
            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").setValue("0000");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        if(white_board){
            move_able=true;
        }
        else {
            move_able=false;
        }
        //data
        mdata= FirebaseDatabase.getInstance().getReference();

//        user
        user= FirebaseAuth.getInstance().getCurrentUser();


//        lấy màu bàn cờ
//        mdata.child("Room").child("Room1").get();

//        bắt trường hợp người dùng ẩn ra khi đang chơi
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                vẽ trên bàn cờ,gọi ondraw
                mChessBoard.invalidate();
            }
        };

        mFilter = new IntentFilter("invalidate");

        mChessBoard = findViewById(R.id.chessboard);

        mChessBoard.mRuleKeeper.mPlayer1Name = findViewById(R.id.player1);
        mChessBoard.mRuleKeeper.mPlayer2Name = findViewById(R.id.player2);

//        gán giá trị cho màu bàn cờ
        mChessBoard.isWhite_chessBoard=white_board;

//      gán your turn
        mChessBoard.mRuleKeeper.turn1 = findViewById(R.id.turn1);
        mChessBoard.mRuleKeeper.turn2 = findViewById(R.id.turn2);
        if(mChessBoard.isWhite_chessBoard)
        {
            mChessBoard.mRuleKeeper.turn1.setText(R.string.turn);
        }
        else {
            mChessBoard.mRuleKeeper.turn2.setText(R.string.turn);
        }

        mChessBoard.mRuleKeeper.setPlayerNames();

        //        khởi tạo lắng nghe
        mdata.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.child("Room"+String.valueOf(ChessActivity.Room)).getChildrenCount()==3&&label==true)
                {
//                    label=false;
//                    Toast.makeText(ChessActivity.this,snapshot.child("Room1").toString(),Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String mv="";
                try {
                    if(white_board){
                        mv=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).child("Black").getValue().toString().trim();
                    }
                    else {
                        mv=snapshot.child("Room"+String.valueOf(ChessActivity.Room)).child("White").getValue().toString().trim();
                    }
                    if(mv !=""){
                        mChessBoard.move_realetime(7-Integer.parseInt(String.valueOf(mv.charAt(0))),
                                7-Integer.parseInt(String.valueOf(mv.charAt(1))),
                                7-Integer.parseInt(String.valueOf(mv.charAt(2))),
                                7-Integer.parseInt(String.valueOf(mv.charAt(3))));
                    }
                }
                catch (Exception e){
                    if(mv !=""){
                        mChessBoard.move_realetime(7-Integer.parseInt(String.valueOf(mv.charAt(0))),
                                7-Integer.parseInt(String.valueOf(mv.charAt(1))),
                                7-Integer.parseInt(String.valueOf(mv.charAt(2))),
                                7-Integer.parseInt(String.valueOf(mv.charAt(3))));
                    }
                }


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

    }

    public static boolean isOrientationInPortraitMode(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Android 7.0 trở lên phải đăng ký
        registerReceiver(mBroadcastReceiver, mFilter);
    }

    @Override
    protected void onStop() {
        //Để ngừng nhận các chương trình phát
        unregisterReceiver(mBroadcastReceiver);
        if(white_board){
            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").removeValue();
        }
        else
        {
            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").removeValue();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(white_board){
            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("White").removeValue();
        }
        else
        {
            mdata.child("Room").child("Room"+String.valueOf(ChessActivity.Room)).child("Black").removeValue();
        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    reset game
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.reset) {
            if (!mChessBoard.mRuleKeeper.GAME_OVER) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.reset_dialog)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mChessBoard.mRuleKeeper.resetGame();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            } else {
                mChessBoard.mRuleKeeper.resetGame();
            }
        }
        return super.onOptionsItemSelected(item);

    }
}