package com.sustech.gamercenter.chinesechess;

import util.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginFrame extends JFrame implements ActionListener {

    JPanel jp1, jp2, jp3;
    JLabel jl1, jl2;
    JButton login, exit;
    JTextField email;
    JPasswordField pwd;

    public LoginFrame() throws Exception {
        jl1 = new JLabel("用户名：");
        jl2 = new JLabel("密码");

        email = new JTextField(10);
        pwd = new JPasswordField(10);

        login = new JButton("登录");
        login.addActionListener(this);
        exit = new JButton("退出");
        exit.addActionListener(this);

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        jp1.add(jl1);
        jp1.add(email);
        jp2.add(jl2);
        jp2.add(pwd);
        jp3.add(login);
        jp3.add(exit);

        this.setLayout(new FlowLayout());
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);


        this.setSize(200, 180);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game.sdk = new DeveloperSDK("11813010@mail.sustech.edu.cn", "james123", "47.115.50.249");
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            try{
                Game.game_id = Game.sdk.getGameByName("中国象棋").getId();
                Game.user_id = Game.sdk.login(email.getText(), String.valueOf(pwd.getPassword()), Game.game_id).getId();
                System.out.println(Game.game_id);
                System.out.println(Game.user_id);

                StringBuffer str = new StringBuffer("登陆成功！\nThe game has DLC shown as below:\n");
                List<GameDLC> DLCs = Game.sdk.getGameDLCs(Game.game_id);
                for (GameDLC dlc : DLCs){
                    str.append(dlc.getId()).append(": ").append(dlc.getName()).append("\n");
                }
                String dlc = JOptionPane.showInputDialog(null, str);
                if (dlc.equals("")){
                    this.setVisible(false);
                    return;
                }

                long dlc_id = Long.parseLong(dlc);

                try{
                    ChessGameFrame.background = Game.sdk.getDLC(Game.user_id, dlc_id);
                    JOptionPane.showMessageDialog(null, "DLC Set Success!");
                }catch (Exception exception){
                    String msg = "You don't have this DLC, input \"yes\" to buy it or other to exit!";
                    String purchase = JOptionPane.showInputDialog(null, msg);

                    if (purchase.equals("yes")){
                        try{
                            Game.sdk.purchaseDLC(Game.user_id, dlc_id);
                            JOptionPane.showMessageDialog(null, "Purchase Success!");
                        }catch (Exception exception1){
                            JOptionPane.showMessageDialog(null, "Purchase Fail!");
                            exception1.printStackTrace();
                        }

                        try{
                            ChessGameFrame.background = Game.sdk.getDLC(Game.user_id, dlc_id);
                            JOptionPane.showMessageDialog(null, "DLC Set Success!");
                        }catch (Exception exception1){
                            JOptionPane.showMessageDialog(null, "DLC download Fail!");
                            exception1.printStackTrace();
                        }
                    }
                }

                this.setVisible(false);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null, "用户名或密码错误");
            }

        }

        if (e.getSource() == exit) {
            this.setVisible(false);
        }
    }

}
