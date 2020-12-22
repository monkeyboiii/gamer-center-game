package com.sustech.gamercenter.chinesechess;

import com.sustech.gamercenter.chinesechess.listener.RestartListener;
import com.sustech.gamercenter.chinesechess.listener.ruleListener;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;


public class Game extends JFrame {
    public static Game game = new Game();


    public Game() {
        setTitle("中国象棋");
        setSize(512, 585);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);//设置窗口

        JButton button = new JButton("开始游戏");
        button.addActionListener(new beginListener());
        button.setLocation(20, 200);
        button.setSize(256, 30);
        button.setBorderPainted(false);
        button.setFont(new Font("华文行楷", 5, 30));
        button.setBackground(Color.white);
        button.setContentAreaFilled(false);
        add(button);

        JButton button2 = new JButton("游戏规则");
        button2.addActionListener(new ruleListener());//   这里需要游戏规则的listener
        button2.setLocation(230, 200);
        button2.setSize(256, 30);
        button2.setFont(new Font("华文行楷", 5, 30));
        button2.setBorderPainted(false);
        button2.setBackground(Color.white);
        button2.setContentAreaFilled(false);
        add(button2);


        // integrating with gamer center sdk
        JButton button3 = new JButton("登入");
        button3.addActionListener(new LoginListener());
        button3.setLocation(250, 30);
        button3.setSize(256, 30);
        button3.setFont(new Font("华文行楷", 5, 30));
        button3.setBorderPainted(false);
        button3.setBackground(Color.white);
        button3.setContentAreaFilled(false);
        add(button3);


        // sequence: 存档
        // com.sustech.gamercenter.chinesechess.chessboard: 棋谱
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                for (int i = 0; i < ChessGameFrame.TOTAL_STEP; i++) {
                    String fileName = "src/main/resources/chessmoveseq/" + i + ".chessmoveseq";
                    File file = new File(fileName);
                    file.delete();
                }
            }

        });


        // background
        //将背景图放在标签里。  
        ImageIcon img = new ImageIcon("src/main/resources/image/back.jpg");
        JLabel imgLabel = new JLabel(img);


        getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。 
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());//设置背景标签的位置  
        Container cp = getContentPane();
        ((Container) cp).setLayout(new BorderLayout());
        ((JPanel) cp).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。  


    }


    public static void main(String[] args) {

        // show panel
        game.setVisible(true);

        // music
        try {
            File file = new File("src/main/resources/audio/chuYu.mp3");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream stream = new BufferedInputStream(fis);
            Player player = new Player(stream);
            player.play();
        } catch (Exception ignore) {
        }
    }


    static class beginListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            InitGlobalFont(new Font("楷体", Font.PLAIN, 24));
            SwingUtilities.invokeLater(() -> {
                // 新开游戏时直接创建新的游戏窗口，如果玩了一会返回再开始则会回到当前玩到的状态，所以在这加个判定，
                // 让每次开始游戏都默认读一次初始的棋谱即可
                ChessGameFrame mainFrame = new ChessGameFrame();

                mainFrame.setVisible(true);
                RestartListener.game = game;
                game.setVisible(false);
            });
        }


        private static void InitGlobalFont(Font font) {
            FontUIResource fontRes = new FontUIResource(font);
            for (Enumeration<Object> keys = UIManager.getDefaults().keys();
                 keys.hasMoreElements(); ) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource) {
                    UIManager.put(key, fontRes);
                }
            }
        }
    }


    static class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);


            });
        }
    }
}


