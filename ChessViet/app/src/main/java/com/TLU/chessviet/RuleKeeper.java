package com.TLU.chessviet;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TLU.chessviet.ChessPieces.Bishop;
import com.TLU.chessviet.ChessPieces.ChessMan;
import com.TLU.chessviet.ChessPieces.King;
import com.TLU.chessviet.ChessPieces.Knight;
import com.TLU.chessviet.ChessPieces.Pawn;
import com.TLU.chessviet.ChessPieces.Queen;
import com.TLU.chessviet.ChessPieces.Rook;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

class RuleKeeper {

    boolean GAME_OVER = false, isGameInCheck = false;
    private Context mContext;
    private ChessBoard mChessBoard;
    TextView mPlayer1Name, mPlayer2Name, turn1, turn2;
    String playing = ChessBoard.WHITE;
    private String mPlayer1, mPlayer2;

    RuleKeeper(ChessBoard chessBoard) {
        mChessBoard = chessBoard;
        mContext = mChessBoard.getContext();
    }

    //chọn tên cho hai người chơi
    void setPlayerNames() {

        //lấy tên
        mPlayer1 = mContext.getString(R.string.player_1_name);
        mPlayer2 = mContext.getString(R.string.player_2_name);

        //chuyển custome_dialog xml sang view java code
        RelativeLayout customDialogLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_dialog, null);
        //ánh xạ hai edittext
        final EditText player1 = customDialogLayout.findViewById(R.id.player1);
        final EditText player2 = customDialogLayout.findViewById(R.id.player2);

        //set name
        new AlertDialog.Builder(mContext)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .setView(customDialogLayout)
                .setCancelable(false)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                        if (player1.length() > 0) {
                            mPlayer1 = player1.getText().toString();
                        }

                        if (player2.length() > 0) {
                            mPlayer2 = player2.getText().toString();
                        }

                        mPlayer1Name.setText(mPlayer1);
                        mPlayer2Name.setText(mPlayer2);

                    }
                })
                .show();
    }

    //    kiểm tra lượt đi xem quân nào là quân trắng
    boolean checkTurn() {
        return mChessBoard.mActivePiece.mColor.equalsIgnoreCase(playing);
    }

    //    thay đổi lượt đi nếu khi có sự di chuyển
    void changeTurn() {
        playing = playing.equalsIgnoreCase(ChessBoard.WHITE) ? ChessBoard.BLACK : ChessBoard.WHITE;
        switchTurn();
    }

    //    đảo lại giá trị text view turn
    private void switchTurn() {
        if (turn1.length() > 0) {
            turn1.setText(null);
            turn2.setText(R.string.turn);
        } else {
            turn2.setText(null);
            turn1.setText(R.string.turn);
        }
    }

    //reset lại game
    void resetGame() {
        GAME_OVER = false;
        mChessBoard.createDefaultGameState();
        if (!playing.equalsIgnoreCase(ChessBoard.WHITE)) {
            changeTurn();
        }
        //vẽ lại board
        mChessBoard.invalidate();
//        hiển thị thông báo phía dưới
        Snackbar.make(mChessBoard, R.string.game_reset, Snackbar.LENGTH_LONG).show();
    }

    //    sử lý khi thua cuộc
    private void gameOver() {
        Snackbar.make(mChessBoard, mContext.getString(R.string.game_over, playing), Snackbar.LENGTH_LONG).show();
        GAME_OVER = true;
    }

    //thông báo khi bị chiếu tướng
    void notifyCheckedOrCheckmate() {

        King king = mChessBoard.findCorrespondingKing();
//       giả sử có tướng
        assert king != null;

        isGameInCheck = king.isChecked(mChessBoard.mGameState, mChessBoard.mAllChessMen.get(king.mOtherColor));

        if (king.isCheckMated(mChessBoard)) {
            gameOver();
            Snackbar.make(mChessBoard, mContext.getString(R.string.over, playing), Snackbar.LENGTH_LONG).show();
        }
        else if (isGameInCheck) {
            Snackbar.make(mChessBoard, mContext.getString(R.string.in_check, playing), Snackbar.LENGTH_LONG).show();
        }

    }

    //Đặt các quân cờ vào đúng vị trí
    ChessMan getChessManForPosition(int row, int column) {

        if (row == 1 || row == 6) {
            return new Pawn(mContext, row > 3);
        } else if (row == 0 || row == 7) {

            switch (column) {
                case 0: case 7:
                    return new Rook(mContext, row > 3);
                case 1: case 6:
                    return new Knight(mContext, row > 3);
                case 2: case 5:
                    return new Bishop(mContext, row > 3);
                case 3:
                    return new Queen(mContext, row > 3);
                case 4:
                    return new King(mContext, row > 3);
            }

        }

        return new ChessMan(mContext);
    }

    //  game bị bế tắc
    void isGameInStalemate() {

        if (!mChessBoard.findCorrespondingKing().isAnyChessManAdvanceable(mChessBoard) ||
                !(checkStalemate(mChessBoard.mAllChessMen.get(ChessBoard.WHITE)) ||
                        checkStalemate(mChessBoard.mAllChessMen.get(ChessBoard.BLACK)))) {

            GAME_OVER = true;
            Snackbar.make(mChessBoard, R.string.stalemate, Snackbar.LENGTH_LONG).show();

        }

    }

    //kiểm tra bế tắc
    private boolean checkStalemate(ArrayList<Position> chessMen) {
        int queens = 0, rooks = 0, knights = 0, pawns = 0, bishops = 0;
        ChessMan chessMan;
        for (Position position : chessMen) {
            chessMan = mChessBoard.mGameState[position.row][position.column];
            if (chessMan.isQueen()) {
                queens++;
            } else if (chessMan.isRook()) {
                rooks++;
            } else if (chessMan.isKnight()) {
                knights++;
            } else if (chessMan.isBishop()) {
                bishops++;
            } else if (chessMan.isPawn()) {
                pawns++;
            }
        }
        return queens > 0 || pawns > 0 || rooks > 0 || knights >= 2 || bishops >= 2 ||
                (knights >= 1 && bishops >= 1);

    }
}