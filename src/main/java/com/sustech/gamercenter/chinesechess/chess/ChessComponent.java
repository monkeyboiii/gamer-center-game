package com.sustech.gamercenter.chinesechess.chess;


import com.sustech.gamercenter.chinesechess.chessboard.ChessboardPoint;
import com.sustech.gamercenter.chinesechess.listener.ChessListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class ChessComponent extends JComponent {
    public final static Dimension CHESS_SIZE = new Dimension(40, 40);
    public final static Color CHESS_COLOR = new Color(254, 218, 164);


    private static List<ChessListener> listenerList = new ArrayList<>();

    private ChessboardPoint chessboardPoint;
    private ChessColor chessColor;
    private boolean selected;
    public boolean canArrive;///


    public static int calculateX(int col) {//
        return (32 + col * (512 - 67) / 9);
    }

    public static int calculateY(int row) {
        return (10 + row * (512 - 10) / 10);//
    }

    //创建chessComponent对象
    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(CHESS_SIZE);

        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            for (ChessListener listener : listenerList) {
                listener.onClick(this);
            }
        }
    }

    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);

    public static boolean registerListener(ChessListener listener) {
        return listenerList.add(listener);
    }

    public static boolean unregisterListener(ChessListener listener) {
        return listenerList.remove(listener);
    }
}
