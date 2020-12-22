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

public class undoListener implements ActionListener {
    ChessboardComponent chessboard = ChessGameFrame.chessboard;

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessGameFrame.chessmove = new StringBuffer();
        ChessGameFrame.button8.setEnabled(true);
        if (ChessGameFrame.TOTAL_STEP == 1) {
            try {
                ChessGameFrame.TOTAL_STEP = 0;
                FileInputStream fis = new FileInputStream(new File("src\\main\\resources\\chessboard\\0.chessboard"));
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                boolean EndAnnotation = false;
                int row = 0;

                boolean nothingExist = false;
                boolean dimension = false;

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
                            continue;
                        }
                        if (!dimension && str.contains("------")) {
                            continue;
                        }
                        row++;

                        if (str.length() == 9)
                            dimension = true;
                        else {
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
                chessboard.repaint();
                JOptionPane.showMessageDialog(null, "悔棋成功!");


            } catch (Exception ignore) {
            }
        } else try {


            chessboard.initChessBoard();

            if (chessboard.getCurrentColor() == ChessColor.RED) {
                chessboard.swapColor();
            }
            if (ChessGameFrame.TOTAL_STEP == 0) {
                JOptionPane.showMessageDialog(null, "已经到底啦！");
            }

            String fileName = "src/main/resources/chessmoveseq/" + (ChessGameFrame.TOTAL_STEP - 1) + ".chessmoveseq";
            FileInputStream fis = new FileInputStream(new File(fileName));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            boolean EndAnnotation = false;
            int STEP = 1;

            while (true) {
                String str = br.readLine();

                if (null == str) break;
                if (str.contains("TOTAL_STEP")) {
                    ChessGameFrame.TOTAL_STEP = Integer.parseInt(str.substring(12));
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
                    } else System.out.println("Invalid Move Pattern at " + str);

                }

                if (str.equals("@@"))
                    EndAnnotation = true;


            }
            br.close();
            if (ChessGameFrame.TOTAL_STEP > 0)
                JOptionPane.showMessageDialog(null, "悔棋成功!");

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

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
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

