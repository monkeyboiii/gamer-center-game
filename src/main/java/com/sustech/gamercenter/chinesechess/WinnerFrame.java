package com.sustech.gamercenter.chinesechess;

import com.sustech.gamercenter.chinesechess.chess.ChessColor;

import javax.swing.*;

public class WinnerFrame extends JFrame {
//    public JTextField winner;
//    public JButton restart;

    public WinnerFrame(ChessColor winColor) {
//        setSize(200,150);
//        setLocation(400, 200);
//        setLayout(new FlowLayout(FlowLayout.CENTER));
//        setLocationRelativeTo(null); // Center the window.
//        add(new JLabel("GameOver"));


        if (winColor == ChessColor.RED) {
//            add(new JLabel("红方胜利!"));
            JOptionPane.showMessageDialog(null, "红方胜利", "中国象棋", JOptionPane.INFORMATION_MESSAGE);
        } else {
//            add(new JLabel("黑方胜利!"));
            JOptionPane.showMessageDialog(null, "黑方胜利", "中国象棋", JOptionPane.INFORMATION_MESSAGE);
        }


//        add(new JButton("确定"));
//        setVisible(false);
    }

//    public class ButtonClickListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent arg0){
//            System.exit(0);
//        }
//    }
}
