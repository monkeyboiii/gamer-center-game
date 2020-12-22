package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.ChessGameFrame;
import com.sustech.gamercenter.chinesechess.WinnerFrame;
import com.sustech.gamercenter.chinesechess.chess.ChessColor;
import com.sustech.gamercenter.chinesechess.chess.ChessComponent;
import com.sustech.gamercenter.chinesechess.chess.GeneralChessComponent;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ChessboardChessListener extends ChessListener {
    private ChessboardComponent chessboardComponent;
    private ChessComponent first;

    public ChessboardChessListener(ChessboardComponent chessboardComponent) {

        this.chessboardComponent = chessboardComponent;
    }

    @Override
    public void onClick(ChessComponent chessComponent) {

        if (first == null) {

            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                for (int i = 0; i < 10; i++) {
                    for (int m = 0; m < 9; m++) {
                        if (chessComponent.canMoveTo(ChessboardComponent.chessboard, new ChessboardPoint(i, m))) {
                            ChessboardComponent.chessboard[i][m].canArrive = true;
                        }

                        if (ChessboardComponent.chessboard[i][m].getChessColor() == chessboardComponent.getCurrentColor()) {
                            ChessboardComponent.chessboard[i][m].canArrive = false;
                        }
                    }
                }


                chessboardComponent.repaint();
            }
        } else {
            if (first == chessComponent) { // Double-click to unselect.
                chessComponent.setSelected(false);
//                int row = chessComponent.getChessboardPoint().getX();
//                int col = chessComponent.getChessboardPoint().getY();
//                ChessboardComponent.rectangles[row][col].setVisible(false);
                first = null;
                for (int i = 0; i < 10; i++) {
                    for (int m = 0; m < 9; m++) {
                        ChessboardComponent.chessboard[i][m].canArrive = false;
                    }
                }
                chessboardComponent.repaint();
            } else if (handleSecond(chessComponent)) {

//                if(chessComponent.getChessColor().equals(ChessColor.BLACK))
//                    com.sustech.gamercenter.chinesechess.ChessGameFrame.currentBlack--;
//                if(chessComponent.getChessColor().equals(ChessColor.RED))
//                    com.sustech.gamercenter.chinesechess.ChessGameFrame.currentRed--;


                if (first.getChessColor().equals(ChessColor.BLACK))
                    ChessGameFrame.chessmove.append(String.format("%d %d %d %d\n", first.getChessboardPoint().getY() + 1, first.getChessboardPoint().getX() + 1,
                            chessComponent.getChessboardPoint().getY() + 1, chessComponent.getChessboardPoint().getX() + 1));

                if (first.getChessColor().equals(ChessColor.RED))
                    ChessGameFrame.chessmove.append(String.format("%d %d %d %d\n", first.getChessboardPoint().getY() * (-1) + 9, first.getChessboardPoint().getX() * (-1) + 10,
                            chessComponent.getChessboardPoint().getY() * (-1) + 9, chessComponent.getChessboardPoint().getX() * (-1) + 10));
                ChessGameFrame.TOTAL_STEP++;

                chessboardComponent.swapChessComponents(first, chessComponent);
                chessboardComponent.swapColor();

                first.setSelected(false);
                first = null;
                String fileName = "src/main/resources/chessmoveseq/" + ChessGameFrame.TOTAL_STEP + ".chessmoveseq";
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

                for (int i = 0; i < 10; i++) {
                    for (int m = 0; m < 9; m++) {
                        ChessboardComponent.chessboard[i][m].canArrive = false;
                    }
                }

                chessboardComponent.repaint();
                try {
                    ChessGameFrame.currentBlack = 0;
                    ChessGameFrame.currentRed = 0;

                    File file = new File("src/main/resources/audio/3885.mp3");
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream stream = new BufferedInputStream(fis);
                    Player player = new Player(stream);
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
                        chessboardComponent.repaint();

                    }
                    if (blackGeneral == 0) {
                        WinnerFrame frame = new WinnerFrame(ChessColor.RED);
//                        frame.setVisible(true);
                        chessboardComponent.repaint();
                    }//                                                   这是将军

                    GeneralChessComponent red = new GeneralChessComponent(new ChessboardPoint(0, 0), new Point(), ChessColor.RED);
                    GeneralChessComponent black = new GeneralChessComponent(new ChessboardPoint(0, 0), new Point(), ChessColor.RED);
                    for (int i = 0; i < 3; i++) {
                        for (int j = 3; j < 6; j++) {
                            if (ChessboardComponent.chessboard[i][j] instanceof GeneralChessComponent) {
                                black = (GeneralChessComponent) ChessboardComponent.chessboard[i][j];
                            }
                        }
                    }
                    for (int i = 7; i < 10; i++) {
                        for (int j = 3; j < 6; j++) {
                            if (ChessboardComponent.chessboard[i][j] instanceof GeneralChessComponent) {
                                red = (GeneralChessComponent) ChessboardComponent.chessboard[i][j];
                            }
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (ChessboardComponent.chessboard[i][j].canMoveTo(ChessboardComponent.chessboard, red.getChessboardPoint())
                                    && ChessboardComponent.chessboard[i][j].getChessColor() != ChessColor.RED && redGeneral != 0 && blackGeneral != 0
                                    && ChessboardComponent.currentColor == ChessColor.RED) {
                                String str = "(" + ChessboardComponent.chessboard[i][j].getChessboardPoint().getX() + "," + ChessboardComponent.chessboard[i][j].getChessboardPoint().getY() + ")";
                                JOptionPane.showMessageDialog(null, str + "将军!");
                            }
                            if (ChessboardComponent.chessboard[i][j].canMoveTo(ChessboardComponent.chessboard, black.getChessboardPoint())
                                    && ChessboardComponent.chessboard[i][j].getChessColor() != ChessColor.BLACK && blackGeneral != 0 && redGeneral != 0
                                    && ChessboardComponent.currentColor == ChessColor.BLACK) {
                                String str = "(" + ChessboardComponent.chessboard[i][j].getChessboardPoint().getX() + "," + ChessboardComponent.chessboard[i][j].getChessboardPoint().getY() + ")";
                                JOptionPane.showMessageDialog(null, str + "将军!");
                            }
                        }
                    }


                } catch (Exception ignore) {
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    for (int m = 0; m < 9; m++) {
                        ChessboardComponent.chessboard[i][m].canArrive = false;
                    }
                }
            }

        }

    }

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboardComponent.getCurrentColor();
    }

    private boolean handleSecond(ChessComponent chessComponent) {
        if (chessComponent.getChessColor() != chessboardComponent.getCurrentColor() &&
                first.canMoveTo(chessboardComponent.getChessboard(), chessComponent.getChessboardPoint())) {
            return true;
        } else {
            return false;
        }
    }
}
