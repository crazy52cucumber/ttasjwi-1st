package ui;

import domain.SelectMember;
import event.MemberUpdateEvent;
import style.ColorSet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MemberUpdateUI extends JFrame {
  private Container cp2 = getContentPane();
  private JPanel p2;
  private JButton bUpdateCheck;
  private JLabel label_Edit_Main, label_PWD, label_PN, label_ADDRESS;
  private JPasswordField passwordField;
  private JTextField textField_PN, textField_ADDRESS;
  private SelectMember member;

  private List<JTextField> textFieldList;


  public MemberUpdateUI(SelectMember member) {
    this.member = member;
    textFieldList = new ArrayList<>();
  }

  public void init() {
    cp2.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.gridheight = 2;
    gbc.insets = new Insets(20, 5, 20, 20);
    label_Edit_Main = new JLabel();
    label_Edit_Main.setText("회원정보 수정 페이지");
    label_Edit_Main.setHorizontalAlignment(SwingConstants.CENTER);
    label_Edit_Main.setVerticalAlignment(SwingConstants.BOTTOM);
    cp2.add(label_Edit_Main, gbc);
    cp2.setBackground(ColorSet.BACKGROUND);
    //cp2.setForeground(c1);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    label_PWD = new JLabel();
    label_PWD.setText("새 비밀번호 :  ");
    cp2.add(label_PWD, gbc);
    //label_PWD.setForeground(c1);

    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    passwordField = new JPasswordField(14);
    cp2.add(passwordField, gbc);
    //passwordField.setForeground(c14);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    label_PN = new JLabel();
    label_PN.setText("새 전화번호 :  ");
    cp2.add(label_PN, gbc);
    //label_PN.setForeground(c1);

    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    textField_PN = new JTextField(14);
    cp2.add(textField_PN, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    label_ADDRESS = new JLabel();
    label_ADDRESS.setText("새 주소 :  ");
    cp2.add(label_ADDRESS, gbc);
    //label_ADDRESS.setForeground(c1);

    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    textField_ADDRESS = new JTextField(14);
    cp2.add(textField_ADDRESS, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    gbc.insets = new Insets(10, 5, 20, 20);
    bUpdateCheck = new JButton("수정하기");
    cp2.add(bUpdateCheck, gbc);
    //bUpdateCheck.setBackground(c16);

    textFieldList.add(passwordField);
    textFieldList.add(textField_PN);
    textFieldList.add(textField_ADDRESS);
    bUpdateCheck.addActionListener(new MemberUpdateEvent(this, member, textFieldList));

    setUI();
  }

  private void setUI() {
    setTitle("내 정보 수정");
    setSize(315, 560);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int centerX = (screenSize.width - getSize().width) / 2;
    int centerY = (screenSize.height - getSize().height) / 2;
    setLocation(centerX + 390, centerY - 23);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public char[] getPasswordFromField() { //비밀번호 수정
    return passwordField.getPassword();
  }

  public String getPhoneNumFromField() {
    return textField_PN.getText();
  }

  public String getAddressFromField() {
    return textField_ADDRESS.getText();
  }
}