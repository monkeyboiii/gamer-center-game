package com.sustech.gamercenter.chinesechess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    JPanel jp1, jp2, jp3;
    JLabel jl1, jl2;
    JButton login, exit;
    JTextField jtx;
    JPasswordField jpw;

    public LoginFrame() {
        jl1 = new JLabel("用户名：");
        jl2 = new JLabel("密码");

        jtx = new JTextField(10);
        jpw = new JPasswordField(10);

        login = new JButton("登录");
        login.addActionListener(this);
        exit = new JButton("退出");
        exit.addActionListener(this);

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        jp1.add(jl1);
        jp1.add(jtx);
        jp2.add(jl2);
        jp2.add(jpw);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            if (jtx.getText().equals("abc") && jpw.getText().equals("123")) {
                JOptionPane.showMessageDialog(null, "登录成功");
            } else {
                JOptionPane.showMessageDialog(null, "用户名或密码错误");
            }
        }

        if (e.getSource() == exit) {
            this.setVisible(false);
        }
    }

}
