package com.TLU.chessviet.ChessPieces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.TLU.chessviet.ChessBoard;
import com.TLU.chessviet.Move;
import com.TLU.chessviet.Position;
import com.TLU.chessviet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChessMan extends AppCompatImageView {

    protected Move[][] mMoves;
    public String mName = "", mColor = "", mOtherColor = "";

    //    để vẽ chấm tròn
    public static Drawable mDefaultDrawable;
    //    vị trí
    public Position mPosition;

    //  các vị trí có thể ăn
    protected CopyOnWriteArrayList<Position> mAdvances = new CopyOnWriteArrayList<>();
    //  các vị trí có thể ăn trong trường hợp đặc biệt(nhốt tướng đối phương)
    protected CopyOnWriteArrayList<Position> mAdvan_speciales = new CopyOnWriteArrayList<>();
    //    biến kiểm tra đã di chuyển chưa sử dụng cho pawn shadown và bắt tốt qua đường
    public boolean hasMoved;

    //    vẽ chấm đỏ
    {
        if (mDefaultDrawable == null) {
            mDefaultDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle);
            mDefaultDrawable.setColorFilter(ChessBoard.selectedFilter);
        }
    }

    //    Các hàm khởi tạo
    public ChessMan(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChessMan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChessMan(Context context) {
        super(context);
        layout(0, 0, ChessBoard.size, ChessBoard.size);
    }

    public ChessMan(Context context, @DrawableRes int drawAbleRes, boolean isWhite) {
        super(context);

        mName = getClass().getSimpleName();
        mColor = isWhite ? ChessBoard.WHITE : ChessBoard.BLACK;
        mOtherColor = isWhite ? ChessBoard.BLACK : ChessBoard.WHITE;

        setImageDrawable(resolveDrawable(drawAbleRes, isWhite));
        layout(0, 0, ChessBoard.size, ChessBoard.size);
        createMoves();
    }

    public int row() {
        return mPosition.row;
    }

    public int column() {
        return mPosition.column;
    }

    //    hiển thị châm tròn đường có thể đi
    public void showAsMove() {
        if (!isChessMan() || isShadowPawn()) {
            setImageDrawable(mDefaultDrawable);
        } else {
            setColorFilter(ChessBoard.selectedFilter);
        }
    }

    //  các chấm đỏ đi
    public void hideAsMove() {
        if (!isChessMan() || isShadowPawn()) {
            setImageDrawable(null);
        } else {
            setColorFilter(isWhite() ? ChessBoard.whiteFilter : ChessBoard.blackFilter);
        }
//        setColorFilter(isWhite() ? ChessBoard.whiteFilter : ChessBoard.blackFilter);
    }

    public boolean isWhite() {
        return mColor.equalsIgnoreCase(ChessBoard.WHITE);
    }
//    xác định màu bàn cờ
    public boolean isRevese() {
        return !ChessBoard.isWhite_chessBoard;
    }

    public Drawable resolveDrawable(@DrawableRes int drawableRes, boolean isWhite) {

        Drawable drawAble = ContextCompat.getDrawable(getContext(), drawableRes).mutate();
        drawAble.setColorFilter(isWhite ? ChessBoard.whiteFilter : ChessBoard.blackFilter);
        return drawAble;

    }

    protected void createMoves() {}

    protected void specialMoves(ChessMan[][] gameState, HashMap<String, ArrayList<Position>> allChessMen) {}

    public void showMoves(ChessMan[][] gameState) {
        for (Position position : mAdvances) {
            gameState[position.row][position.column].showAsMove();
        }
    }

    //ẩn các chấm đỏ
    public void hideMoves(ChessMan[][] gameState) {
        for (Position position : mAdvances) {
            gameState[position.row][position .column].hideAsMove();
        }
    }

    //    kiểm tra xem có thể ăn không
    public boolean canAdvanceTo(Position position) {
        if (mAdvances.size() > 0) {
            for (Position p : mAdvances) {
                if (p.getPosition() == position.getPosition()) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean canAdvance_special_To(Position position) {
        if (mAdvan_speciales.size() > 0) {
            for (Position p : mAdvan_speciales) {
                if (p.getPosition() == position.getPosition()) {
                    return true;
                }
            }
        }
        return false;
    }

//cập nhật lại các nước đi
    public void refreshMoves(ChessMan[][] gameState, HashMap<String, ArrayList<Position>> allChessMen) {

        if (isChessMan()) {
            mAdvances.clear();
            mAdvan_speciales.clear();
            int row, column;
            ChessMan chessMan;
            for (Move[] moves : mMoves) {
                for (Move move : moves) {
                    if (move != null) {
                        row = mPosition.row + move.row;
                        column = mPosition.column + move.column;
                        if (row >= 0 && column >= 0 && row < ChessBoard.NO_OF_ROWS && column < ChessBoard.NO_OF_COLUMNS) {
                            chessMan = gameState[row][column];
                            if (!isAlly(chessMan) &&
                                    (!move.isOnlyAllowedFromDefaultState || !hasMoved) &&
                                    (!move.isOnlyAllowedIfOccupied || chessMan.isChessMan()) &&
                                    (!move.isOnlyAllowedIfNotOccupied || !chessMan.isChessMan())) {
                                mAdvances.add(chessMan.mPosition);
                            }
                            else {
                                if((!move.isOnlyAllowedFromDefaultState || !hasMoved) &&
                                        (!move.isOnlyAllowedIfOccupied || chessMan.isChessMan()) &&
                                        (!move.isOnlyAllowedIfNotOccupied || !chessMan.isChessMan())){
                                    mAdvan_speciales.add(chessMan.mPosition);
                                }
                                else {
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                        if (chessMan.isChessMan() && !chessMan.isShadowPawn()) {
                            break;
                        }
                    }
                }
            }
            specialMoves(gameState, allChessMen);
        }
    }

    public void advance(ChessMan[][] gameState, Position position) {

        hideMoves(gameState);

        if (position != Pawn.mShadowPawn && Pawn.mShadowPawn != null) {
            gameState[Pawn.mShadowPawn.row][Pawn.mShadowPawn.column] = new ChessMan(getContext());
            gameState[Pawn.mShadowPawn.row][Pawn.mShadowPawn.column].setPosition(Pawn.mShadowPawn);
            Pawn.mShadowPawn = null;
        }

        gameState[mPosition.row][mPosition.column] = new ChessMan(getContext());
        gameState[mPosition.row][mPosition.column].setPosition(mPosition);

        gameState[position.row][position.column] = this;
        setPosition(position);

        if (!hasMoved) {
            hasMoved = true;
        }

    }

    public boolean isChessMan() {
        return !mName.isEmpty();
    }

    public boolean isAlly(ChessMan chessMan) {
        return mColor.equalsIgnoreCase(chessMan.mColor);
    }

    public boolean isKing() {
        return this instanceof King;
    }

    public boolean isQueen() {
        return this instanceof Queen;
    }

    public boolean isRook() {
        return this instanceof Rook;
    }

    public boolean isKnight() {
        return this instanceof Knight;
    }

    public boolean isBishop() {
        return this instanceof Bishop;
    }

    public boolean isPawn() {
        return this instanceof Pawn;
    }

    public boolean isShadowPawn() {
        return this instanceof Pawn.ShadowPawn;
    }

    public void setPosition(Position position) {
        mPosition = position;
    }
}
