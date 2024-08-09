package ui;

import domain.SelectMember;
import event.MemberInfoEvent;
import repository.StatusRepository;
import style.ColorSet;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class MemberInfoUI extends JFrame {
  private DefaultHeaderUI defaultHeader;
  private StatusRepository statusRepository;
  private SelectMember member;

  public MemberInfoUI(SelectMember member) {
    this.member = member;
    statusRepository = new StatusRepository();
    init();
  }

  Container cp = getContentPane();
  JPanel p1;
  JButton bChildUpdate, bMemberUpdate;
  JLabel label_info, label_ID, label_ID1, label_NAME, label_NAME1, label_PN, label_PN1, label_ADDRESS, label_ADDRESS1, label_JOIN_DATE, label_JOIN_DATE1, label_C_ID, label_C_ID1;

  void init() {
    label_info = new JLabel();
    label_info.setText("☆나의 정보★");
    label_info.setOpaque(true);
    label_info.setBackground(ColorSet.SUBTITLE);
    label_info.setHorizontalAlignment(SwingConstants.CENTER);
    label_info.setVerticalAlignment(SwingConstants.BOTTOM);

    label_ID = new JLabel();
    label_ID.setOpaque(true);
    label_ID.setBackground(ColorSet.IND1);
    label_ID.setText("♣ 아이디  :  ");

    label_ID1 = new JLabel();
    label_ID1.setOpaque(true);
    label_ID1.setBackground(ColorSet.VAL2);
    label_ID1.setText(member.getId());

    label_NAME = new JLabel();
    label_NAME.setOpaque(true);
    label_NAME.setBackground(ColorSet.IND2);
    label_NAME.setText("♣ 이름  :  ");

    label_NAME1 = new JLabel();
    label_NAME1.setOpaque(true);
    label_NAME1.setBackground(ColorSet.VAL1);
    label_NAME1.setText(member.getName());

    label_PN = new JLabel();
    label_PN.setOpaque(true);
    label_PN.setBackground(ColorSet.IND1);
    label_PN.setText("♣ 전화번호  :  ");

    label_PN1 = new JLabel();
    label_PN1.setOpaque(true);
    label_PN1.setBackground(ColorSet.VAL2);
    label_PN1.setText(member.getPhoneNum());

    label_ADDRESS = new JLabel();
    label_ADDRESS.setOpaque(true);
    label_ADDRESS.setBackground(ColorSet.IND2);
    label_ADDRESS.setText("♣ 주소 :  ");

    label_ADDRESS1 = new JLabel();
    label_ADDRESS1.setOpaque(true);
    label_ADDRESS1.setBackground(ColorSet.VAL1);
    label_ADDRESS1.setText(member.getAddress());


    label_JOIN_DATE = new JLabel();
    label_JOIN_DATE.setOpaque(true);
    label_JOIN_DATE.setBackground(ColorSet.IND1);
    label_JOIN_DATE.setText("♣ 가입일  :  ");

    label_JOIN_DATE1 = new JLabel();
    label_JOIN_DATE1.setOpaque(true);
    label_JOIN_DATE1.setBackground(ColorSet.VAL2);
    Date memberJoinDate = member.getJoinDate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = dateFormat.format(memberJoinDate);
    label_JOIN_DATE1.setText(formattedDate);


    label_C_ID = new JLabel();
    label_C_ID.setOpaque(true);
    label_C_ID.setBackground(ColorSet.IND2);
    label_C_ID.setText("♣ 자녀 이름  :  ");

    label_C_ID1 = new JLabel();
    label_C_ID1.setOpaque(true);
    label_C_ID1.setBackground(ColorSet.VAL1);
    String childName = statusRepository.findChildName(member.getTspoonNo());
    label_C_ID1.setText(childName);

    cp.setBackground(ColorSet.SUBTITLE);
    cp.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH; //X,Y 축 다 채울지 정하기
    gbc.weightx = 1;
    gbc.weighty = 1;

    //제목
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.gridheight = 2;
    cp.add(label_info, gbc);

    //아이디
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_ID, gbc);

    //이름
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_NAME, gbc); //수정

    //전화번호
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_PN, gbc); //수정

    //주소
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_ADDRESS, gbc);

    //가입일
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_JOIN_DATE, gbc); //수정

    //자녀 이름
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_C_ID, gbc);


    //여기부터 (2열)
    //아이디
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_ID1, gbc);

    //이름
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_NAME1, gbc);

    //전화번호
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_PN1, gbc);

    //주소
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_ADDRESS1, gbc);

    //가입일
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_JOIN_DATE1, gbc);

    //자녀 이름
    gbc.gridx = 1;
    gbc.gridy = 7;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    cp.add(label_C_ID1, gbc);


    bChildUpdate = new JButton("자녀등록");
    bChildUpdate.setPreferredSize(new Dimension(150, 40));
    bChildUpdate.addActionListener(new MemberInfoEvent("bChildUpdate", member));

    bMemberUpdate = new JButton("회원정보 수정");
    bMemberUpdate.setPreferredSize(new Dimension(150, 40));
    bMemberUpdate.addActionListener(new MemberInfoEvent("bMemberUpdate", member));

    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 0;
    gbc.gridy = 8;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    cp.add(bChildUpdate, gbc);

    gbc.gridx = 0;
    gbc.gridy = 9;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    cp.add(bMemberUpdate, gbc);

    setUI();
  }

  void setUI() {

    setTitle("티스푼 마이페이지");
    setSize(400, 710);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int centerX = (screenSize.width - getSize().width) / 2;
    int centerY = (screenSize.height - getSize().height) / 2;
    setLocation(centerX + 390, centerY - 23);
    setVisible(true);
    setResizable(false);
  }
}
