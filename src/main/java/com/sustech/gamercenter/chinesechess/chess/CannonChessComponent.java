package com.sustech.gamercenter.chinesechess.chess;


import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class CannonChessComponent extends ChessComponent {
    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            int count = 0;
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    count++;
                }
            }
            if (count > 1) return false;
            if (count == 1 &&
                    chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                return false;
            }

            if (count == 0 && !(chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                return false;
            }

            if (count == 0 &&
                    chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                return true;
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            int count = 0;
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    count++;
                }
            }
            if (count > 1) return false;
            if (count == 1 &&
                    chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent) return false;
            if (count == 0 && !(chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                return false;
            }

            if (count == 0 &&
                    chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent) return true;
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(CHESS_COLOR);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(getChessColor().getColor());
        g.drawString("炮", 8, 28); // FIXME: Use library to find the correct offset.
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
