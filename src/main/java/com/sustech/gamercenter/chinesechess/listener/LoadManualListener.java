package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;
import com.sustech.gamercenter.chinesechess.WinnerFrame;
import com.sustech.gamercenter.chinesechess.chess.ChessColor;
import com.sustech.gamercenter.chinesechess.chess.ChessComponent;
import com.sustech.gamercenter.chinesechess.chess.EmptySlotComponent;
import com.sustech.gamercenter.chinesechess.chess.GeneralChessComponent;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class LoadManualListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessGameFrame.chessmove = new StringBuffer();
        String str1 = JOptionPane.showInputDialog("你的棋谱的名字是什么？（后缀.chessmoveseq不要加）");

//        File file = new File("src/main/resources/chessmoveseq/" + str1.trim() + ".chessmoveseq");

        if (str1 == null || str1.equals(""))
            JOptionPane.showMessageDialog(null, "名字不能为空!");
//        else if (!file.exists())
//            JOptionPane.showMessageDialog(null, "名为 " + str1 + ".chessmoveseq 棋谱不存在!");
        else try {


                ChessboardComponent chessboard = ChessGameFrame.chessboard;
                chessboard.initChessBoard();

                if (chessboard.getCurrentColor() == ChessColor.RED) {
                    chessboard.swapColor();
                }

//                FileInputStream fis = new FileInputStream(new File("src/main/resources/chessmoveseq/" + str1.trim() + ".chessmoveseq"));
//                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            InputStreamReader isr = new InputStreamReader(
                    ChessGameFrame.sdk.cloudDownload(ChessGameFrame.game.getId(),
                            ChessGameFrame.user.getId(), str1.trim() + ".chessmoveseq")
            );
                BufferedReader br = new BufferedReader(isr);

                boolean EndAnnotation = false;
                int STEP = 1;

                while (true) {
                    String str = br.readLine();

                    if (null == str) break;
                    if (str.contains("TOTAL_STEP")) {
//                        com.sustech.gamercenter.chinesechess.ChessGameFrame.TOTAL_STEP=Integer.parseInt(str.substring(12));
                    }

                    if (str.contains("#")) {//判断注释
                        int ignore = str.indexOf("#");
                        if (ignore == 0) continue;
                        str = str.substring(0, ignore);
                    }


                    if (EndAnnotation) {
//                    time();
                        if (str.equals("")) continue;
                        ChessGameFrame.chessmove.append(str).append("\n");
                        String[] place = str.split(" ");
                        int originalX = Integer.parseInt(place[0]);
                        int originalY = Integer.parseInt(place[1]);
                        int destinationX = Integer.parseInt(place[2]);
                        int destinationY = Integer.parseInt(place[3]);


                        if (originalX < 1 || originalX > 9 || originalY < 1 || originalY > 10
                                || destinationX < 1 || destinationX > 9 || destinationY < 1 || destinationY > 10) {
                            System.out.println("Position Out Of Range at " + str);
                            continue;
                        }

                        if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                            int c = originalX;
                            originalX = originalY - 1;
                            originalY = c - 1;
                            int c1 = destinationX;
                            destinationX = destinationY - 1;
                            destinationY = c1 - 1;
                        }
                        if (chessboard.getCurrentColor() == ChessColor.RED) {
                            int c = originalX;
                            originalX = 10 - originalY;
                            originalY = 9 - c;
                            int c1 = destinationX;
                            destinationX = 10 - destinationY;
                            destinationY = 9 - c1;
                        }
                        ChessComponent originComponent = chessboard.getChessboard()[originalX][originalY];
                        ChessComponent destinationComponent = chessboard.getChessboard()[destinationX][destinationY];
                        if (originComponent instanceof EmptySlotComponent
                                || !originComponent.getChessColor().equals(chessboard.getCurrentColor())) {
                            System.out.println("Invalid From Position at " + str);
                            continue;
                        }
                        if (destinationComponent.getChessColor().equals(chessboard.getCurrentColor())) {
                            System.out.println("Invalid To Position at " + str);
                            continue;
                        }
                        if (originComponent.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(destinationX, destinationY))) {
                            chessboard.swapChessComponents(originComponent, destinationComponent);
                            chessboard.swapColor();
                            ChessGameFrame.chessboard.repaint();
                            STEP++;
                            String fileName = ChessGameFrame.TOTAL_STEP + ".chessmoveseq";
                            if (ChessGameFrame.TOTAL_STEP > 0)
                                try {//写棋谱；
                                    FileOutputStream fos = new FileOutputStream(new File(fileName));
                                    OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                                    BufferedWriter output = new BufferedWriter(osr);
                                    output.write(String.format("@TOTAL_STEP=%d\n@@\n\n", ChessGameFrame.TOTAL_STEP));
                                    output.write(ChessGameFrame.chessmove.toString());
                                    output.flush();
                                    output.close();
                                } catch (Exception ignored) {
                                }//                               这是添加内容（写棋谱）
                            ChessGameFrame.TOTAL_STEP++;

                        } else System.out.println("Invalid Move Pattern at " + str);

                    }

                    if (str.equals("@@"))
                        EndAnnotation = true;

                }
                br.close();
                JOptionPane.showMessageDialog(null, "名为 " + str1 + ".chessmoveseq 棋谱已成功导入!");

            } catch (Exception ex) {
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
                ChessGameFrame.chessboard.repaint();
            }
            if (blackGeneral == 0) {
                WinnerFrame frame = new WinnerFrame(ChessColor.RED);
//                        frame.setVisible(true);
                ChessGameFrame.chessboard.repaint();
            }//

        } catch (Exception ignore) {
        }

    }

    public boolean time() {
        long start = System.currentTimeMillis();
        while (true) {
            long end = System.currentTimeMillis();
            long time = end - start;
            if (time > 1000) return true;
        }
    }
}
