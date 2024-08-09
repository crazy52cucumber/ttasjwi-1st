package ui;

import domain.SelectMember;
import event.BackEvent;
import event.HeaderEvent;
import style.ColorSet;

import javax.swing.*;
import java.awt.*;

public class DefaultHeaderUI extends JPanel {
  private String status;
  private Object currentObj;
  private Object previousObj;
  private SelectMember member;


  public DefaultHeaderUI(String status, Object currentObj, Object previousObj, SelectMember member) {
    this.status = status;
    this.currentObj = currentObj;
    this.previousObj = previousObj;
    if (member != null) {
      this.member = member;
    }

    setBounds(0, 0, 400, 65);
    setLayout(null);
    setBackground(ColorSet.HEADER);

    JLabel backImageBtn = new JLabel("뒤로가기");
    ImageIcon imageIcon = imageSetSize("icon/back2.png", 25, 25);
    backImageBtn.setIcon(imageIcon);
    backImageBtn.setBounds(25, 23, 25, 25);
    backImageBtn.addMouseListener(new BackEvent(currentObj, previousObj));

    JLabel centerLabel = new JLabel();
    ImageIcon logo = imageSetSize("icon/logospoonD.png", 50, 50);
    centerLabel.setIcon(logo);
    centerLabel.setBounds(170, 4, 55, 65);

    if (status.equals("first")) {
      add(centerLabel);
    }

    if (status.equals("register")) {
      add(backImageBtn);
      add(centerLabel);
    }

    if (status.equals("login-main")) {
      add(centerLabel);

      // 유저
      JLabel userLabel = new JLabel("유저");
      ImageIcon userIcon = imageSetSize("icon/user.png", 40, 40);
      userLabel.setIcon(userIcon);
      userLabel.setBounds(280, 10, 40, 40);
      userLabel.addMouseListener(new HeaderEvent(currentObj, null, member));
      add(userLabel);

      // 로그아웃
      JLabel logoutLabel = new JLabel("로그아웃");
      ImageIcon logoutIcon = imageSetSize("icon/logoutM.png", 40, 40);
      logoutLabel.setIcon(logoutIcon);
      logoutLabel.setBounds(340, 10, 40, 40);
      logoutLabel.addMouseListener(new HeaderEvent(currentObj, previousObj, member));
      add(logoutLabel);
    }

    if (status.equals("login")) {
      add(backImageBtn);
      add(centerLabel);

      // 유저
      JLabel userLabel = new JLabel("유저");
      ImageIcon userIcon = imageSetSize("icon/user.png", 40, 40);
      userLabel.setIcon(userIcon);
      userLabel.setBounds(280, 10, 40, 40);
      userLabel.addMouseListener(new HeaderEvent(currentObj, null, member));
      add(userLabel);

      // 로그아웃
      JLabel logoutLabel = new JLabel("로그아웃");
      ImageIcon logoutIcon = imageSetSize("icon/logoutM.png", 40, 40);
      logoutLabel.setIcon(logoutIcon);
      logoutLabel.setBounds(340, 10, 40, 40);
      logoutLabel.addMouseListener(new HeaderEvent(currentObj, previousObj, member));
      add(logoutLabel);
    }
  }

  private ImageIcon imageSetSize(String imagePath, int w, int h) {
    Image originImage = new ImageIcon("./images/" + imagePath).getImage();
    Image resizeBackImage = originImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    return new ImageIcon(resizeBackImage);
  }
}
