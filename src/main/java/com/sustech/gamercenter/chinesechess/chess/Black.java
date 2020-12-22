package com.sustech.gamercenter.chinesechess.chess;


import com.sustech.gamercenter.chinesechess.ChessGameFrame;

import javax.swing.*;
import java.awt.*;

public class Black extends JComponent {
    public Black() {
        setSize(1000, 700);
        setLocation(490, 120);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString("黑方棋子\n" + ChessGameFrame.currentBlack, 2, 48); // FIXME: Use library to find the correct offset.

    }
}
