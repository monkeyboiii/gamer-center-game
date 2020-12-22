package com.sustech.gamercenter.chinesechess.chess;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;

import javax.swing.*;
import java.awt.*;

public class Red extends JComponent {
    public Red() {
        setSize(1000, 700);
        setLocation(490, 320);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString("红方棋子\n" + ChessGameFrame.currentRed, 2, 48); // FIXME: Use library to find the correct offset.

    }
}
