package com.sustech.gamercenter.chinesechess.chessboard;


import com.sustech.gamercenter.chinesechess.chess.*;
import com.sustech.gamercenter.chinesechess.listener.ChessListener;
import com.sustech.gamercenter.chinesechess.listener.ChessboardChessListener;

import javax.swing.*;
import java.awt.*;

public class ChessboardComponent extends JComponent {
    public static ChessComponent[][] chessboard = new ChessComponent[10][9];
    public static ChessColor currentColor = ChessColor.BLACK;


    public ChessboardComponent(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);

        ChessListener chessListener = new ChessboardChessListener(this);
        ChessComponent.registerListener(chessListener);
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));

            }
        }

        add(new currentRed());
        add(new currentBlack());
        initChessBoard();
    }

    public void initChessBoard() {
//        com.sustech.gamercenter.chinesechess.chessboard=new ChessComponent[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                remove(chessboard[i][j]);
                initEmptyBoard(i, j);

            }
        }
        initChariot(0, 0, ChessColor.BLACK);
        initChariot(0, 8, ChessColor.BLACK);
        initChariot(9, 0, ChessColor.RED);
        initChariot(9, 8, ChessColor.RED);

        initAdvisor(0, 3, ChessColor.BLACK);
        initAdvisor(0, 5, ChessColor.BLACK);
        initAdvisor(9, 3, ChessColor.RED);
        initAdvisor(9, 5, ChessColor.RED);

        initGeneral(0, 4, ChessColor.BLACK);
        initGeneral(9, 4, ChessColor.RED);

        initSoldier(3, 0, ChessColor.BLACK);
        initSoldier(3, 2, ChessColor.BLACK);
        initSoldier(3, 4, ChessColor.BLACK);
        initSoldier(3, 6, ChessColor.BLACK);
        initSoldier(3, 8, ChessColor.BLACK);
        initSoldier(6, 0, ChessColor.RED);
        initSoldier(6, 2, ChessColor.RED);
        initSoldier(6, 4, ChessColor.RED);
        initSoldier(6, 6, ChessColor.RED);
        initSoldier(6, 8, ChessColor.RED);

        initCannon(2, 1, ChessColor.BLACK);
        initCannon(2, 7, ChessColor.BLACK);
        initCannon(7, 1, ChessColor.RED);
        initCannon(7, 7, ChessColor.RED);

        initHorse(0, 1, ChessColor.BLACK);
        initHorse(0, 7, ChessColor.BLACK);
        initHorse(9, 1, ChessColor.RED);
        initHorse(9, 7, ChessColor.RED);

        initElephant(0, 2, ChessColor.BLACK);
        initElephant(0, 6, ChessColor.BLACK);
        initElephant(9, 2, ChessColor.RED);
        initElephant(9, 6, ChessColor.RED);

    }

    public void initialize() {
        initChariot(0, 0, ChessColor.BLACK);
        initChariot(0, 8, ChessColor.BLACK);
        initChariot(9, 0, ChessColor.RED);
        initChariot(9, 8, ChessColor.RED);

        initAdvisor(0, 3, ChessColor.BLACK);
        initAdvisor(0, 5, ChessColor.BLACK);
        initAdvisor(9, 3, ChessColor.RED);
        initAdvisor(9, 5, ChessColor.RED);

        initGeneral(0, 4, ChessColor.BLACK);
        initGeneral(9, 4, ChessColor.RED);

        initSoldier(3, 0, ChessColor.BLACK);
        initSoldier(3, 2, ChessColor.BLACK);
        initSoldier(3, 4, ChessColor.BLACK);
        initSoldier(3, 6, ChessColor.BLACK);
        initSoldier(3, 8, ChessColor.BLACK);
        initSoldier(6, 0, ChessColor.RED);
        initSoldier(6, 2, ChessColor.RED);
        initSoldier(6, 4, ChessColor.RED);
        initSoldier(6, 6, ChessColor.RED);
        initSoldier(6, 8, ChessColor.RED);

        initCannon(2, 1, ChessColor.BLACK);
        initCannon(2, 7, ChessColor.BLACK);
        initCannon(7, 1, ChessColor.RED);
        initCannon(7, 7, ChessColor.RED);

        initHorse(0, 1, ChessColor.BLACK);
        initHorse(0, 7, ChessColor.BLACK);
        initHorse(9, 1, ChessColor.RED);
        initHorse(9, 7, ChessColor.RED);

        initElephant(0, 2, ChessColor.BLACK);
        initElephant(0, 6, ChessColor.BLACK);
        initElephant(9, 2, ChessColor.RED);
        initElephant(9, 6, ChessColor.RED);


    }

    public ChessComponent[][] getChessboard() {
        return chessboard;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessboard[row][col] != null) {
            remove(chessboard[row][col]);
        }
        add(chessboard[row][col] = chessComponent);
    }

//    public void addRectangle(Rectangle rectangle){
//        int row = rectangle.getChessboardPoint().getX();
//        int col = rectangle.getChessboardPoint().getY();
//        add(rectangles[row][col] = rectangle);
//    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        //如果chess2非空就remove再创建一个空的，把chess1放到chess2原来的位置上去，实现吃子
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessboard[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessboard[row2][col2] = chess2;

    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK;
    }

    public void initChariot(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ChariotChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initEmptyBoard(int row, int col) {
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col));
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initAdvisor(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new AdvisorChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initGeneral(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new GeneralChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initSoldier(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new SoldierChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initHorse(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new HorseChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initElephant(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ElephantChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initCannon(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new CannonChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /*
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBoardLine(g, 0, 0, 9, 0);
        paintBoardLine(g, 0, 8, 9, 8);
        paintHalfBoard(g, 0);
        paintHalfBoard(g, 5);
        paintKingSquare(g, 1, 4);
        paintKingSquare(g, 8, 4);
    }

    private void paintHalfBoard(Graphics g, int startRow) {
        for (int row = startRow; row < startRow + 5; row++) {
            paintBoardLine(g, row, 0, row, 8);
        }
        for (int col = 0; col < 9; col++) {
            paintBoardLine(g, startRow, col, startRow + 4, col);
        }
    }

    private void paintKingSquare(Graphics g, int centerRow, int centerCol) {
        paintBoardLine(g, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1);
        paintBoardLine(g, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1);
    }

    private void paintBoardLine(Graphics g, int rowFrom, int colFrom, int rowTo, int colTo) {
        int offsetX = ChessComponent.CHESS_SIZE.width / 2, offsetY = ChessComponent.CHESS_SIZE.height / 2;
        Point p1 = calculatePoint(rowFrom, colFrom), p2 = calculatePoint(rowTo, colTo);
        g.drawLine(p1.x + offsetX, p1.y + offsetY, p2.x + offsetX, p2.y + offsetY);
    }
*/
    public Point calculatePoint(int row, int col) {
        int c = 32 + col * (getWidth() - 67) / 9;
        int c1 = 10 + row * (getHeight() - 10) / 10;
        return new Point(32 + col * (getWidth() - 67) / 9, 10 + row * (getHeight() - 10) / 10);
    }
}
