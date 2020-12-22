package com.sustech.gamercenter.chinesechess.chess;

import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class GeneralChessComponent extends ChessComponent {
    public GeneralChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (flyingGeneral(chessboard, destination)) {
            return true;
        }

        if (destination.getY() < 3 || destination.getY() > 5) {//左右边界
            return false;
        }

        if (this.getChessColor() == ChessColor.BLACK && destination.getX() > 2) {//黑 下边界
            return false;
        }

        if (this.getChessColor() == ChessColor.RED && destination.getX() < 7) {//红 上边界
            return false;
        }

        if (destination.getX() == source.getX() && Math.abs(destination.getY() - source.getY()) == 1) {
            return true;
        }

        if (destination.getY() == source.getY() && Math.abs(destination.getX() - source.getX()) == 1) {
            return true;
        }
        return false;
    }

    public boolean flyingGeneral(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getY() != destination.getY()) {
            return false;
        }

        int col = source.getY();
        for (int row = Math.min(source.getX(), destination.getX()) + 1;
             row < Math.max(source.getX(), destination.getX()); row++) {
            if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                return false;
            }
        }

        if (this.getChessColor() == ChessColor.RED) {
            for (int m = 0; m <= 2; m++) {
                if (chessboard[m][col].getClass() == GeneralChessComponent.class &&
                        chessboard[m][col].getChessboardPoint() == destination) {
                    return true;
                }
            }
        } else if (this.getChessColor() == ChessColor.BLACK) {
            for (int m = 7; m <= 9; m++) {
                if (chessboard[m][col].getClass() == GeneralChessComponent.class &&
                        chessboard[m][col].getChessboardPoint() == destination) {
                    return true;
                }
            }
        }

        return false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(CHESS_COLOR);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(getChessColor().getColor());
        if (this.getChessColor() == ChessColor.RED)
            g.drawString("帅", 8, 28); // FIXME: Use library to find the correct offset.
        if (this.getChessColor() == ChessColor.BLACK)
            g.drawString("将", 8, 28); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the com.sustech.gamercenter.chinesechess.chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (canArrive) {
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

    }

}
