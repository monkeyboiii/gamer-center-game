package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.chess.ChessColor;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class surrender implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ChessboardComponent.currentColor == ChessColor.BLACK)
            JOptionPane.showMessageDialog(null, "红方胜利", "中国象棋", JOptionPane.INFORMATION_MESSAGE);
        if (ChessboardComponent.currentColor == ChessColor.RED)
            JOptionPane.showMessageDialog(null, "黑方胜利", "中国象棋", JOptionPane.INFORMATION_MESSAGE);

    }
}
