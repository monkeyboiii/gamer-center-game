package com.sustech.gamercenter.chinesechess.listener;

import com.sustech.gamercenter.chinesechess.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            try {
                LoginFrame loginFrame = new LoginFrame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}