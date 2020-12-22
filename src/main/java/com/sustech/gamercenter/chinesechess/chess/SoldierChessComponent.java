package com.sustech.gamercenter.chinesechess.chess;


import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class SoldierChessComponent extends ChessComponent {
    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor() == ChessColor.BLACK) {
            if (source.getY() == destination.getY() && destination.getX() - source.getX() == 1) {
                return true;
            }

            if (getChessboardPoint().getX() >= 5) {
                if (source.getX() == destination.getX() &&
                        Math.abs(source.getY() - destination.getY()) == 1) {
                    return true;
                }
            }
        } else {
            if (source.getY() == destination.getY() && destination.getX() - source.getX() == -1) {
                return true;
            }

            if (getChessboardPoint().getX() <= 4) {
                if (source.getX() == destination.getX() &&
                        Math.abs(source.getY() - destination.getY()) == 1) {
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
        g.drawString("兵", 8, 28); // FIXME: Use library to find the correct offset.
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
