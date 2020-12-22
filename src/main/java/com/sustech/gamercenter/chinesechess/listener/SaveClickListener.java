package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;
import com.sustech.gamercenter.chinesechess.chess.*;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class SaveClickListener implements ActionListener {//                   存档
    ChessboardComponent chessboard = ChessGameFrame.chessboard;

    @Override
    public void actionPerformed(ActionEvent e) {
        String str1 = JOptionPane.showInputDialog("给你的棋局起个名字吧（后缀.chessboard不要加）");
        if(str1==null|| str1.equals(""))
            JOptionPane.showMessageDialog(null, "名字不能为空!");

        else try {

            FileOutputStream fos = new FileOutputStream(new File(str1+".com.sustech.gamercenter.chinesechess.chessboard"));
            OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter output = new BufferedWriter(osr);
            ChessComponent[][] chessComponents = chessboard.getChessboard();
            String lastMover = "";
            if (chessboard.getCurrentColor() == ChessColor.BLACK) lastMover = "RED";
            if (chessboard.getCurrentColor() == ChessColor.RED) lastMover = "BLACK";
            lastMover = String.format("@LAST_MOVER=%s\n", lastMover);
            output.write(lastMover + "@@\n\n");
            for (int i = 0; i < 10; i++) {
                if (i == 5)
                    output.write("---------\n");
                for (int j = 0; j < 9; j++) {
                    output.write(getName(chessComponents[i][j]));
                }
                output.write("\n");
            }
            output.flush();
            output.close();
            JOptionPane.showMessageDialog(null, "名为 "+str1+".com.sustech.gamercenter.chinesechess.chessboard 的棋盘已成功保存!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getName(ChessComponent chessComponent) {
        if (chessComponent instanceof EmptySlotComponent) return ".";
        if (chessComponent instanceof AdvisorChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "A";
            if (chessComponent.getChessColor() == ChessColor.RED) return "a";
        }
        if (chessComponent instanceof GeneralChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "G";
            if (chessComponent.getChessColor() == ChessColor.RED) return "g";
        }
        if (chessComponent instanceof ElephantChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "E";
            if (chessComponent.getChessColor() == ChessColor.RED) return "e";
        }
        if (chessComponent instanceof HorseChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "H";
            if (chessComponent.getChessColor() == ChessColor.RED) return "h";
        }
        if (chessComponent instanceof ChariotChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "C";
            if (chessComponent.getChessColor() == ChessColor.RED) return "c";
        }
        if (chessComponent instanceof CannonChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "N";
            if (chessComponent.getChessColor() == ChessColor.RED) return "n";
        }
        if (chessComponent instanceof SoldierChessComponent) {
            if (chessComponent.getChessColor() == ChessColor.BLACK) return "S";
            if (chessComponent.getChessColor() == ChessColor.RED) return "s";
        }
        return "";
    }
}
