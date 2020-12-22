package com.sustech.gamercenter.chinesechess.chess;



import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.*;

public class currentRed extends JComponent {
    public currentRed(){
        setSize(70, 70);
        setLocation(520, 400);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ChessboardComponent.currentColor == ChessColor.RED){
            g.setColor(new Color(217, 125, 80));

            g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
            g.setColor(Color.RED);
            setFont(new Font("华文新魏", 5, 40));
            g.drawString("红", 13, 48); // FIXME: Use library to find the correct offset.
        }else {
            g.setColor(Color.gray);

            g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.BLACK);
            g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
            g.setColor(Color.BLACK);
            setFont(new Font("华文新魏", 5, 40));
            g.drawString("红", 13, 48); // FIXME: Use library to find the correct offset.
        }
    }
}
