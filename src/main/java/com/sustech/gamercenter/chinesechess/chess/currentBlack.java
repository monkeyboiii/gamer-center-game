package com.sustech.gamercenter.chinesechess.chess;



import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.*;

public class currentBlack extends JComponent {
    public currentBlack(){
        setSize(70, 70);
        setLocation(520, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ChessboardComponent.currentColor == ChessColor.BLACK){
            g.setColor(new Color(217, 125, 80));
        }else {
            g.setColor(Color.gray);
        }
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(Color.BLACK);
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(Color.BLACK);
        setFont(new Font("楷体", Font.BOLD, 40));
        g.drawString("黑", 13, 48); // FIXME: Use library to find the correct offset.

    }
}
