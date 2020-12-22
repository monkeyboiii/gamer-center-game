package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;
import com.sustech.gamercenter.chinesechess.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class SaveManualListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        String str1 = JOptionPane.showInputDialog("给你的棋谱起个名字吧（后缀.chessmoveseq不要加）");

        if (Game.game_id == -1 || Game.user_id == -1)
            JOptionPane.showMessageDialog(null, "请先登录!");
        else if (str1 == null || str1.equals(""))
            JOptionPane.showMessageDialog(null, "名字不能为空!");
        else try {//写棋谱；
            String result = String.format("@TOTAL_STEP=%d\n@@\n\n", ChessGameFrame.TOTAL_STEP);
            result = result + ChessGameFrame.chessmove.toString();
            InputStream inputStream = new ByteArrayInputStream(result.getBytes());
            Game.sdk.cloudUpload(Game.game_id, Game.user_id, inputStream, str1.trim() + ".chessmoveseq");

//            FileOutputStream fos = new FileOutputStream(new File(str1 + ".chessmoveseq"));
//            OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//            BufferedWriter output = new BufferedWriter(osr);
//            output.write(String.format("@TOTAL_STEP=%d\n@@\n\n", ChessGameFrame.TOTAL_STEP));
//            output.write(ChessGameFrame.chessmove.toString());
//            output.flush();
//            output.close();

            JOptionPane.showMessageDialog(null, "名为 " + str1 + ".chessmoveseq 棋谱已成功保存!");

        } catch (Exception ignored) {
                System.out.println("保存失败！");
        }//                               这是添加内容（写棋谱）

    }
}
