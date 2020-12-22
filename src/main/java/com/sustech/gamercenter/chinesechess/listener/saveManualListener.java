package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class saveManualListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        String str1 = JOptionPane.showInputDialog("给你的棋谱起个名字吧（后缀.chessboard不要加）");
        if (str1 == null || str1.equals(""))
            JOptionPane.showMessageDialog(null, "名字不能为空!");

        else try {//写棋谱；
            FileOutputStream fos = new FileOutputStream(new File(str1 + ".chessmoveseq"));
            OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter output = new BufferedWriter(osr);
            output.write(String.format("@TOTAL_STEP=%d\n@@\n\n", ChessGameFrame.TOTAL_STEP));
            output.write(ChessGameFrame.chessmove.toString());
            output.flush();
            output.close();
            JOptionPane.showMessageDialog(null, "名为 " + str1 + ".chessmoveseq 棋谱已成功保存!");

        } catch (Exception ignored) {
        }//                               这是添加内容（写棋谱）

    }
}
