package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UITest extends JFrame {
  UITest() {
    JPanel logoPanel = new JPanel();
    logoPanel.setBounds(59, 10, 314, 205);

    Image logo = new ImageIcon("images/티스푼.png").getImage();
    Image resizeLogo = logo.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    JLabel logoLabel = new JLabel("와이라노", new ImageIcon(resizeLogo), SwingConstants.CENTER);
    logoPanel.add(logoLabel);

    add(logoPanel);


    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 710);

  }

  public static void main(String[] args) {
    new UITest();
  }
}
