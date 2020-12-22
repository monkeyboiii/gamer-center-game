package com.sustech.gamercenter.chinesechess.chess;


import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;


public class AdvisorChessComponent extends ChessComponent {
    public AdvisorChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (destination.getY() < 3 || destination.getY() > 5) {
            return false;
        }
        if (getChessColor() == ChessColor.BLACK && destination.getX() > 2) {
            return false;
        }
        if (getChessColor() == ChessColor.RED && destination.getX() < 7) {
            return false;
        }
        if (Math.abs(destination.getX() - source.getX()) == 1 &&
                Math.abs(destination.getY() - source.getY()) == 1) {
            return true;
        }
        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(CHESS_COLOR);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(getChessColor().getColor());
        g.drawString("士", 8, 28); // FIXME: Use library to find the correct offset.
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

