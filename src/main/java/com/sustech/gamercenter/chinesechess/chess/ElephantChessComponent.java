package com.sustech.gamercenter.chinesechess.chess;


import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class ElephantChessComponent extends ChessComponent {
    public ElephantChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        for (int x = -2; x < 3; x = x + 4) {
            for (int y = -2; y < 3; y = y + 4) {
                if (x + source.getX() < 0 || x + source.getX() > 9 || y + source.getY() < 0 || y + source.getY() > 8)
                    continue;
                if (getChessColor().equals(ChessColor.BLACK) && source.getX() + x > 4) continue;
                if (getChessColor().equals(ChessColor.RED) && source.getX() + x < 5) continue;
                if (destination.getY() == source.getY() + y && destination.getX() == source.getX() + x) {
                    if (chessboard[source.getX() + x / Math.abs(x)][source.getY() + y / Math.abs(y)] instanceof EmptySlotComponent)
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
        g.drawString("相", 8, 28); // FIXME: Use library to find the correct offset.
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
