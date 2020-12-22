package com.sustech.gamercenter.chinesechess;

import com.sustech.gamercenter.chinesechess.chess.*;
import com.sustech.gamercenter.chinesechess.chessboard.ChessboardComponent;
import com.sustech.gamercenter.chinesechess.listener.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import model.*;
import util.*;

public class ChessGameFrame extends JFrame {
    public static ChessboardComponent chessboard = new ChessboardComponent(512, 512);
    public static StringBuffer chessmove;
    public static int TOTAL_STEP = 0;
    public static JButton button8;
    public static int currentRed = 16;
    public static int currentBlack = 16;
    public static DeveloperSDK sdk;
    public static User user;
    public static model.Game game;

    static {
        try {
            sdk = new DeveloperSDK("calvin@gmail.com", "calvin123", "10.21.128.97");
            game = sdk.getGameByName("chinese%20chess");
            user = sdk.login("charlie@foxmail.com", "charlie123");
            System.out.println(game.getId());
            System.out.println(user.getId());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public ChessGameFrame() {
        InitGlobalFont(new Font("楷体", Font.PLAIN, 24));
//        setBackground(ChessComponent.CHESS_COLOR);

        // background image
        // 将背景图放在标签里
        ImageIcon img = new ImageIcon("src/main/resources/image/chessboard.jpg");
        JLabel imgLabel = new JLabel(img);
        setBackground(ChessComponent.CHESS_COLOR);


        getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jframe的LayeredPane面板里。  
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());//设置背景标签的位置  
        Container cp = getContentPane();
        ((Container) cp).setLayout(new BorderLayout());

        ((JPanel) cp).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。  

        ImageIcon im = new ImageIcon("src/main/resources/image/chinesechess/imgs/th.jpg");//这是背景图片  
        JLabel imgLabel1 = new JLabel(im);
        setBackground(ChessComponent.CHESS_COLOR);

        //将背景图放在标签里。  

        getLayeredPane().add(imgLabel1, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jframe的LayeredPane面板里。  
        imgLabel1.setBounds(0, 0, im.getIconWidth(), im.getIconHeight());//设置背景标签的位置  
        Container cp1 = getContentPane();
        ((Container) cp1).setLayout(new BorderLayout());

        ((JPanel) cp1).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。  


        setTitle("Chinese Chess");
        setSize(840, 580);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton button = new JButton("中国象棋");//绘画save按钮               存档按钮
//        button1.setLocation(600, 0);
//        button1.setSize(120, 70);
        button.setBounds(0, 510, 500, 20);
        button.addActionListener(null);
        button.setBorderPainted(false);
        button.setBackground(Color.white);
        button.setFont(new Font("隶书", 1, 30));
        button.setContentAreaFilled(false);
        add(button);

//
//        ChessboardComponent com.sustech.gamercenter.chinesechess.chessboard = new ChessboardComponent(512, 512);
        add(chessboard);

        BufferedImage img1 = null;
        Image smaller1 = null;
        try {
            img1 = ImageIO.read(new File("src/main/resources/image/button1.png"));
            smaller1 = smaller(img1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label1 = new JLabel((new ImageIcon(smaller1)));
        getContentPane().add(label1);
        label1.setBounds(600, 0, 446, 625);


        JButton button1 = new JButton("存档");//绘画save按钮               存档按钮
//        button1.setLocation(600, 0);
//        button1.setSize(120, 70);
        button1.setBounds(600, 0, 300, 60);
        button1.addActionListener(new SaveClickListener());
        add(button1);
        button1.setBorderPainted(false);
        button1.setBackground(Color.white);
        button1.setContentAreaFilled(false);

//        button1.setBackground(new Color());
        BufferedImage img2 = null;
        Image smaller2 = null;
        try {
            img2 = ImageIO.read(new File("src/main/resources/image/button2.png"));
            smaller2 = smaller(img2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label2 = new JLabel((new ImageIcon(smaller2)));
        getContentPane().add(label2);
        label2.setBounds(600, 60, 446, 625);


        JButton button2 = new JButton("读档");//绘画load按钮                读档按钮
//        button2.setLocation(600, 70);
//        button2.setSize(120, 70);
        button2.setBounds(600, 60, 300, 60);
        button2.addActionListener(new LoadFileListener());
        add(button2);
        button2.setBorderPainted(false);
        button2.setBackground(Color.white);
        button2.setContentAreaFilled(false);


        BufferedImage img3 = null;
        Image smaller3 = null;
        try {
            img3 = ImageIO.read(new File("src/main/resources/image/button3.png"));
            smaller3 = smaller(img3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label3 = new JLabel((new ImageIcon(smaller3)));
        getContentPane().add(label3);
        label3.setBounds(600, 120, 446, 625);


        JButton button3 = new JButton("存棋谱");
//        button3.setLocation(600, 140);
//        button3.setSize(120, 70);
        button3.setBounds(600, 120, 300, 60);
        button3.addActionListener(new saveManualListener());
        add(button3);
        button3.setBorderPainted(false);
        button3.setBackground(Color.white);
        button3.setContentAreaFilled(false);


        BufferedImage img4 = null;
        Image smaller4 = null;
        try {
            img4 = ImageIO.read(new File("src/main/resources/image/button4.png"));
            smaller4 = smaller(img4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label4 = new JLabel((new ImageIcon(smaller4)));
        getContentPane().add(label4);
        label4.setBounds(600, 180, 446, 625);


        JButton button4 = new JButton("读棋谱");
//        button4.setLocation(600, 210);
//        button4.setSize(120, 70);
        button4.setBounds(600, 180, 300, 60);
        button4.addActionListener(new LoadManualListener());
        add(button4);
        button4.setBorderPainted(false);
        button4.setBackground(Color.white);
        button4.setContentAreaFilled(false);


        BufferedImage img5 = null;
        Image smaller5 = null;
        try {
            img5 = ImageIO.read(new File("src/main/resources/image/button5.png"));
            smaller5 = smaller(img5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label5 = new JLabel((new ImageIcon(smaller5)));
        getContentPane().add(label5);
        label5.setBounds(600, 240, 446, 625);


        JButton button5 = new JButton("悔棋");
//        button5.setLocation(600, 280);
//        button5.setSize(120, 70);
        button5.setBounds(600, 240, 300, 60);
        button5.addActionListener(new undoListener());
        add(button5);
        button5.setBorderPainted(false);
        button5.setBackground(Color.white);
        button5.setContentAreaFilled(false);


        BufferedImage img8 = null;
        Image smaller8 = null;
        try {
            img8 = ImageIO.read(new File("src/main/resources/image/button9.png"));
            smaller8 = smaller(img8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label8 = new JLabel((new ImageIcon(smaller8)));
        getContentPane().add(label8);
        label8.setBounds(600, 480, 446, 625);

        button8 = new JButton("");//撤回悔棋
//        button8.setLocation(600, 300);
//        button8.setSize(120, 60);
        button8.setBounds(600, 300, 300, 60);
        button8.addActionListener(new backUndoListener());
        button8.setFont(new Font("华文行楷", 5, 20));
        button8.setEnabled(false);
        button8.setBorderPainted(false);
        button8.setBackground(Color.white);
        button8.setContentAreaFilled(false);
        add(button8);

        BufferedImage img6 = null;
        Image smaller6 = null;
        try {
            img6 = ImageIO.read(new File("src/main/resources/image/button6.png"));
            smaller6 = smaller(img6);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label6 = new JLabel((new ImageIcon(smaller6)));
        getContentPane().add(label6);
        label6.setBounds(600, 300, 446, 625);

        JButton button6 = new JButton("");/////结束
//        button6.setLocation(600, 455);
//        button6.setSize(120, 45);
        button6.setBounds(600, 480, 300, 60);
        button6.setFont(new Font("华文行楷", 5, 30));
        button6.addActionListener(new CloseListener());
        button6.setBorderPainted(false);
        button6.setBackground(Color.white);
        button6.setContentAreaFilled(false);
        add(button6);


        BufferedImage img7 = null;
        Image smaller7 = null;
        try {
            img7 = ImageIO.read(new File("src/main/resources/image/button7.png"));
            smaller7 = smaller(img7);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label7 = new JLabel((new ImageIcon(smaller7)));
        getContentPane().add(label7);
        label7.setBounds(600, 360, 446, 625);


        JButton button7 = new JButton("");//返回
//        button7.setLocation(600, 410);
//        button7.setSize(120, 45);
        button7.setBounds(600, 420, 300, 60);
        button7.setFont(new Font("华文行楷", 5, 30));
        button7.addActionListener(new RestartListener(this));
        button7.setBorderPainted(false);
        button7.setBackground(Color.white);
        button7.setContentAreaFilled(false);
        add(button7);


        BufferedImage img9 = null;
        Image smaller9 = null;
        try {
            img9 = ImageIO.read(new File("src/main/resources/image/button8.png"));
            smaller9 = smaller(img9);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label9 = new JLabel((new ImageIcon(smaller9)));
        getContentPane().add(label9);
        label9.setBounds(600, 420, 446, 625);

        JButton button9 = new JButton("初始化");
//        button9.setLocation(600, 360);
//        button9 .setFont(new  java.awt.Font("华文行楷",  5,  25));
        button9.setBounds(600, 360, 300, 60);
        button9.setSize(120, 50);
        button9.setBorderPainted(false);
        button9.setBackground(Color.white);
        button9.setContentAreaFilled(false);
        button9.addActionListener(new renewListener());

        add(button9);

        BufferedImage img10 = null;
        Image smaller10 = null;
        try {
            img10 = ImageIO.read(new File("src/main/resources/image/buttonRenShu.png"));
            smaller10 = smaller(img10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setLayout(null);

        JLabel label10 = new JLabel((new ImageIcon(smaller10)));
        getContentPane().add(label10);
        label10.setBounds(380, 220, 446, 625);


        JButton button10 = new JButton("认输");
//        button9.setLocation(600, 360);
//        button9 .setFont(new  java.awt.Font("华文行楷",  5,  25));
        button10.setBounds(500, 220, 100, 80);
        button10.setBorderPainted(false);
        button10.setBackground(Color.white);
        button10.setContentAreaFilled(false);
        button10.addActionListener(new surrender());

        add(button10);


        add(new currentRed());
        add(new currentBlack());
        add(new Red());
        add(new Black());
//        JButton button5 = new JButton("初始化");
//        button5.setLocation(490, 60);
//        button5.setSize(80, 30);
//        button5.addActionListener(new initListener());
//        add(button5);

        chessmove = new StringBuffer();

//        addRedAdvisor(9, 3);

//        JLabel statusLabel = new JLabel("Sample label");
//        statusLabel.setLocation(0, 400);
//        statusLabel.setSize(200, 10);
//        add(statusLabel);
//
//        JButton button = new JButton("...");
//        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
//        button.setLocation(370, 400);
//        button.setSize(20, 10);
//        add(button);
    }

    public Image smaller(Image img) {
        int height = 625;
        int width = img.getWidth(null) * 625 / img.getHeight(null);
//        System.out.println(width);
        Image smaller = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return smaller;
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


    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        InitGlobalFont(new Font("楷体", Font.PLAIN, 24));
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame();

            mainFrame.setVisible(true);

        });

        File file = new File("src/main/resources/audio/chuYu.mp3");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream stream = new BufferedInputStream(fis);
        Player player = new Player(stream);
        player.play();
    }
}