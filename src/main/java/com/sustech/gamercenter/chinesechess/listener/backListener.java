package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;
import com.sustech.gamercenter.chinesechess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class backListener implements ActionListener {

    ChessGameFrame chessGameFrame;

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        JPanel jp = (JPanel) btn.getParent();
        JPanel jFrame = (JPanel) jp.getParent();
        int jcount = jFrame.getComponentCount();
        for (int i = 0; i < jcount; i++) {
            Component comp = jFrame.getComponent(i);
            if (comp instanceof ChessGameFrame) {
                chessGameFrame = (ChessGameFrame) comp;
                System.out.print(i);
            }
        }
        chessGameFrame.setVisible(false);
        Game.game.setVisible(true);

    }
}
