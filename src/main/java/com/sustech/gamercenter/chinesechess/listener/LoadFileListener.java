package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;
import com.sustech.gamercenter.chinesechess.Game;
import com.sustech.gamercenter.chinesechess.WinnerFrame;
import com.sustech.gamercenter.chinesechess.chess.*;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;
import javazoom.jl.player.Player;
import util.DeveloperSDK;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LoadFileListener implements ActionListener {//                   读档
    ChessboardComponent chessboard = ChessGameFrame.chessboard;
    @Override
    public void actionPerformed(ActionEvent e){

        if (Game.game_id == -1 || Game.user_id == -1){
            JOptionPane.showMessageDialog(null, "请先登录!");
            return;
        }

        List<String> records = null;
        try {
            records = Game.sdk.cloudList(Game.game_id, Game.user_id);
            StringBuilder result = new StringBuilder();
            for (String record : records)
                if (record.endsWith(".chessboard"))
                    result.append(record).append("\r\n");
            JOptionPane.showMessageDialog(null, result.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ChessGameFrame.chessmove = new StringBuffer();
        String str1 = JOptionPane.showInputDialog("你的棋谱的名字是什么？（后缀.chessboard不要加）");

        if (str1 == null || str1.equals(""))
            JOptionPane.showMessageDialog(null, "名字不能为空!");
        else
            try {
                chessboard.initChessBoard();
                InputStreamReader isr = new InputStreamReader(
                        Game.sdk.cloudDownload(Game.game_id, Game.user_id, str1.trim() + ".chessboard")
                );
                BufferedReader br = new BufferedReader(isr);
                boolean EndAnnotation = false;
                int row = 0;

                boolean nothingExist = false;
                boolean dimension = false;
                boolean SpaceExist = true;
                boolean riverExist = false;

                while (true) {
                    String str = br.readLine();
                    if (null == str) break;

                    if (str.contains("#")) {//判断注释
                        int ignore = str.indexOf("#");
                        if (ignore == 0) continue;
                        str = str.substring(0, ignore);
                    }

                    if (str.equals("") && !nothingExist) {
                        nothingExist = true;
                        continue;
                    }
                    if (str.contains("LAST_MOVER")) {
                        if (str.contains("@LAST_MOVER=BLACK") && chessboard.getCurrentColor() == ChessColor.BLACK)
                            chessboard.swapColor();
                        if (str.contains("@LAST_MOVER=RED") && chessboard.getCurrentColor() == ChessColor.RED)
                            chessboard.swapColor();
                    }
                    if (EndAnnotation) {

                        if (str.equals("---------")) {
                            riverExist = true;
                            continue;
                        }
                        if (!dimension && str.contains("------")) {
                            riverExist = true;
                            continue;
                        }
                        row++;

                        if (str.length() == 9)
                            dimension = true;
                        else {
                            SpaceExist = false;
                            continue;
                        }

                        for (int i = 0; i < 9; i++) {
                            if (row < 11)
                                transform(row - 1, i, str.charAt(i));
                        }

                    }
                    if (str.equals("@@"))
                        EndAnnotation = true;
                }
                br.close();
                if (!riverExist) {
                    JOptionPane.showMessageDialog(null, "楚河汉界缺失!");
                    throw new Exception("River Missing!");
                }
                if (row != 10) {
//                dimension=false;
                    JOptionPane.showMessageDialog(null, "棋局长宽错误!");
                    throw new Exception("Invalid Dimension!");

                }
                if (!dimension) {
                    JOptionPane.showMessageDialog(null, "棋局长宽错误!");
                    throw new Exception("Invalid Dimension!");
                } else if (!SpaceExist) {
                    JOptionPane.showMessageDialog(null, "空白符丢失!");
                    throw new Exception("Space Missing!");
                }

//                if(com.sustech.gamercenter.chinesechess.chessboard.getChessboard()[9][8]==null)
//                    throw new Exception("Invalid Dimension");//没有写棋盘长度判断。

                //判断棋子数量
                int bSoldier = 0;
                int bAdvisor = 0;
                int bCannon = 0;
                int bChariot = 0;
                int bElephant = 0;
                int bGeneral = 0;
                int bHorse = 0;
                int rSoldier = 0;
                int rAdvisor = 0;
                int rCannon = 0;
                int rChariot = 0;
                int rElephant = 0;
                int rGeneral = 0;
                int rHorse = 0;

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 9; j++) {
                        ChessComponent chessComponent = chessboard.getChessboard()[i][j];
                        if (chessComponent instanceof AdvisorChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bAdvisor++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rAdvisor++;
                        }
                        if (chessComponent instanceof GeneralChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bGeneral++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rGeneral++;
                        }
                        if (chessComponent instanceof ElephantChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bElephant++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rElephant++;
                        }
                        if (chessComponent instanceof HorseChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bHorse++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rHorse++;
                        }
                        if (chessComponent instanceof ChariotChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bChariot++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rChariot++;
                        }
                        if (chessComponent instanceof CannonChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bCannon++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rCannon++;
                        }
                        if (chessComponent instanceof SoldierChessComponent) {
                            if (chessComponent.getChessColor() == ChessColor.BLACK) bSoldier++;
                            if (chessComponent.getChessColor() == ChessColor.RED) rSoldier++;
                        }
                    }
                }
                if (bSoldier > 5 || rSoldier > 16 || bAdvisor > 2 || rAdvisor > 2 || bCannon > 2 || rCannon > 2 || bChariot > 2 || rChariot > 2
                        || bElephant > 2 || rElephant > 2 || bGeneral != 1 || rGeneral != 1 || bHorse > 2 || rHorse > 2) {
                    JOptionPane.showMessageDialog(null, "棋子数量错误!");
                    throw new Exception("Invalid Chess Amount");
                }


                chessboard.repaint();
                JOptionPane.showMessageDialog(null, "名为 " + str1 + ".chessboard 的棋盘已成功读入!");

            } catch (Exception ex) {
                System.out.println("载入失败！");
                JOptionPane.showMessageDialog(null, "载入失败!");
                ex.printStackTrace();
            }
        try {
            ChessGameFrame.currentBlack = 0;
            ChessGameFrame.currentRed = 0;

            File file22 = new File("src/main/resources/audio/3885.mp3");
            FileInputStream fis22 = new FileInputStream(file22);
            BufferedInputStream stream22 = new BufferedInputStream(fis22);
            Player player = new Player(stream22);
            player.play();

            //判定结束
            int redGeneral = 0;
            int blackGeneral = 0;
            for (int i = 0; i < 10; i++) {
                for (int m = 0; m < 9; m++) {
                    if (ChessboardComponent.chessboard[i][m].getClass() == GeneralChessComponent.class &&
                            ChessboardComponent.chessboard[i][m].getChessColor() == ChessColor.RED) {
                        redGeneral++;
                    }

                    if (ChessboardComponent.chessboard[i][m].getClass() == GeneralChessComponent.class &&
                            ChessboardComponent.chessboard[i][m].getChessColor() == ChessColor.BLACK) {
                        blackGeneral++;
                    }
                    if (ChessboardComponent.chessboard[i][m].getChessColor().equals(ChessColor.BLACK)) {
                        ChessGameFrame.currentBlack++;
                    }
                    if (ChessboardComponent.chessboard[i][m].getChessColor().equals(ChessColor.RED)) {
                        ChessGameFrame.currentRed++;
                    }
                }
            }
            if (redGeneral == 0) {
                WinnerFrame frame = new WinnerFrame(ChessColor.BLACK);
//                        frame.setVisible(true);
                chessboard.repaint();
            }
            if (blackGeneral == 0) {
                WinnerFrame frame = new WinnerFrame(ChessColor.RED);
//                        frame.setVisible(true);
                chessboard.repaint();
            }//

        } catch (Exception ignore) {
        }
    }

    private void transform(int row, int col, char c) {
        if (c == '.') chessboard.initEmptyBoard(row, col);
        if (c == 'G') chessboard.initGeneral(row, col, ChessColor.BLACK);
        if (c == 'g') chessboard.initGeneral(row, col, ChessColor.RED);
        if (c == 'A') chessboard.initAdvisor(row, col, ChessColor.BLACK);
        if (c == 'E') chessboard.initElephant(row, col, ChessColor.BLACK);
        if (c == 'C') chessboard.initChariot(row, col, ChessColor.BLACK);
        if (c == 'N') chessboard.initCannon(row, col, ChessColor.BLACK);
        if (c == 'S') chessboard.initSoldier(row, col, ChessColor.BLACK);
        if (c == 'H') chessboard.initHorse(row, col, ChessColor.BLACK);
        if (c == 'a') chessboard.initAdvisor(row, col, ChessColor.RED);
        if (c == 'e') chessboard.initElephant(row, col, ChessColor.RED);
        if (c == 'h') chessboard.initHorse(row, col, ChessColor.RED);
        if (c == 'c') chessboard.initChariot(row, col, ChessColor.RED);
        if (c == 'n') chessboard.initCannon(row, col, ChessColor.RED);
        if (c == 's') chessboard.initSoldier(row, col, ChessColor.RED);
    }
}

