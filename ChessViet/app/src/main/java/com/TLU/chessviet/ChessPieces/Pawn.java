package com.TLU.chessviet.ChessPieces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.TLU.chessviet.ChessBoard;
import com.TLU.chessviet.Move;
import com.TLU.chessviet.Position;
import com.TLU.chessviet.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Pawn extends ChessMan {

    protected static Position mShadowPawn;

    public Pawn(Context context, boolean isWhite) {
        super(context, R.drawable.ic_pawn, isWhite);
    }

    @Override
    protected void createMoves() {

        boolean reverse = isWhite();

        if(isRevese())
        {
            mMoves = new Move[3][2];
            mMoves[0][0] = new Move(!reverse ? -1 : 1, 0, false, false, true);
            mMoves[0][1] = new Move(!reverse ? -2 : 2, 0, true, false, true);
            mMoves[1][0] = new Move(!reverse ? -1 : 1, 1, false, true);
            mMoves[2][0] = new Move(!reverse ? -1 : 1, -1, false, true);
        }
        else
        {
            mMoves = new Move[3][2];
            mMoves[0][0] = new Move(reverse ? -1 : 1, 0, false, false, true);
            mMoves[0][1] = new Move(reverse ? -2 : 2, 0, true, false, true);
            mMoves[1][0] = new Move(reverse ? -1 : 1, 1, false, true);
            mMoves[2][0] = new Move(reverse ? -1 : 1, -1, false, true);
        }

    }

    @Override
    public void advance(ChessMan[][] gameState, Position position) {

        // bắt tốt qua đường
//        shawdow: những quân tốt chưa được đi
        if (gameState[position.row][position.column].isShadowPawn()) {
            Pawn.ShadowPawn shadowPawn = (Pawn.ShadowPawn) gameState[position.row][position.column];
            gameState[shadowPawn.parent.row][shadowPawn.parent.column] = new ChessMan(getContext());
            gameState[shadowPawn.parent.row][shadowPawn.parent.column].setPosition(shadowPawn.parent);
        }
//      board reverse
        int enPassantRow;
        if(isRevese()){
            enPassantRow = isWhite() ? position.row -1 : position.row + 1;
        }
        else {
            enPassantRow = isWhite() ? position.row + 1 : position.row - 1;
        }
        boolean emPassant = enPassantRow >= 0 && enPassantRow < ChessBoard.NO_OF_ROWS &&
                (position.row == mPosition.row - 2 || position.row == mPosition.row + 2);

        promote(gameState, position);
        super.advance(gameState, position);

        if (emPassant) {
            ShadowPawn shadowPawn = new ShadowPawn(getContext());
            mShadowPawn = new Position(enPassantRow, position.column);
            shadowPawn.setPosition(mShadowPawn);
            shadowPawn.parent = mPosition;
            gameState[enPassantRow][position.column] = shadowPawn;
        }

    }

    //    khi tốt ở dòng đầu hoặc cuối
    private void promote(final ChessMan[][] gameState, Position position) {

        if (position.row == 0 || position.row == 7) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.promotion)
                    .setItems(R.array.promotions, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gameState[row()][column()] = exchangeChessMan(i);
                            gameState[row()][column()].setPosition(mPosition);

                            Intent intent = new Intent();
                            intent.setAction("invalidate");
                            getContext().sendBroadcast(intent);
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }

    }

    //    phong hâu hoặc xe,...
    private ChessMan exchangeChessMan(int i) {

        if (i == 0) {
            return new Queen(getContext(), isWhite());
        } else if (i == 1) {
            return new Rook(getContext(), isWhite());
        } else if (i == 2) {
            return new Knight(getContext(), isWhite());
        } else if (i == 3) {
            return new Bishop(getContext(), isWhite());
        }

        return null;

    }

    public class ShadowPawn extends ChessMan {

        Position parent;

        public ShadowPawn(Context context) {
            super(context);
        }

        @Override
        public boolean isChessMan() {
            return true;
        }

        @Override
        public void refreshMoves(ChessMan[][] gameState, HashMap<String, ArrayList<Position>> allChessMen) {

        }
    }

}
