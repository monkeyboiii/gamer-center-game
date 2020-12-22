package com.sustech.gamercenter.chinesechess.listener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ruleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String str="-------------------------游戏规则---------------------------------游戏规则-----------------------------------游戏规则----------------------------\n" +
                "\n" +
                "1、中国象棋是由两人轮流走子，在战法上遵循古代孙子兵法中的“不战而屈人之兵，善之善者也”的作战思想，以“将死”或“困毙”对方将（帅）为胜的一种二人对抗性游戏。\n" +
                "对局时，由执红棋的一方先走，双方轮流各走一招，直至分出胜、负、和，对局即终了。在棋战中，人们可以从攻与防、虚与实、整体与局部等复杂关系的变化中提升思维能力。\n" +
                "\n" +
                "2、行棋规定：对局时，由执红棋的一方先走，双方轮流走一步。轮到走棋的一方，将某个棋子从一个交叉点走到另一个交叉点，或者吃掉对方的棋子而占领其交叉点，都算走了一着。\n" +
                "双方各走一着，称为一个回合。走一着棋时，如果己方棋子能够走到的位置有对方棋子存在，就可以把对方棋子吃掉而占领那个位置。\n" +
                "\n" +
                "一方的棋子攻击对方的帅（将），并在下一着要把它吃掉，称为“照将”，或简称“将”。“照将”不必声明。被“照将”的一方必须立即“应将”，即用自己的着法去化解被“将”的状态。\n" +
                "如果被“照将”而无法“应将”，就算被“将死”。\n" +
                "\n" +
                "3、\n" +
                "将或帅：移动范围：它只能在王宫内移动。\n" +
                "\n" +
                "     移动规则：它每一步只可以水平或垂直移动一点。\n" +
                "\n" +
                "士：移动范围：它只能在王宫内移动。\n" +
                "\n" +
                "     移动规则：它每一步只可以沿对角线方向移动一点。\n" +
                "\n" +
                "象：移动范围：河界的一侧。\n" +
                "\n" +
                "     移动规则：它每一步只可以沿对角线方向移动两点，另外，在移动的过程中不能够穿越障碍。\n" +
                "\n" +
                "马：移动范围：任何位置\n" +
                "\n" +
                "     移动规则：每一步只可以水平或垂直移动一点，再按对角线方面向左或者右移动。另外，在移动的过程中不能够穿越障碍。\n" +
                "\n" +
                "车：移动范围：任何位置\n" +
                "\n" +
                "     移动规则：可以水平或垂直方向移动任意个无阻碍的点。\n" +
                "\n" +
                "炮：移动范围：任何位置\n" +
                "\n" +
                "     移动规则：移动起来和车很相似，但它必须跳过一个棋子来吃掉对方的一个棋子。\n" +
                "\n" +
                "兵：移动范围：任何位置\n" +
                "\n" +
                "     移动规则：每步只能向前移动一点。过河以后，它便增加了向左右移动的能力，兵不允许向后移动。";
        ImageIcon image = new ImageIcon("src/main/resources/image/icon.ico");

        BufferedImage imag = null;
        try {
            imag = ImageIO.read(new File("src/main/resources/image/icon.ico"));
        } catch (IOException ex) {
        }

        JOptionPane.showMessageDialog(null,str,"中国象棋",3,(Icon)imag);
    }
}
