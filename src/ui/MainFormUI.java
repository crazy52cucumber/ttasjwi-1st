package ui;

import domain.SelectMember;
import repository.AttendanceRepository;
import event.AttendanceEvent;
import event.MainEvent;
import style.ColorSet;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class MainFormUI extends JFrame {

  //컴포넌트 생성
  private static final JButton boardButton = new JButton("게시판");
  private static final JButton classButton = new JButton("학습현황");

  private static final JLabel profileLabel = new JLabel();
  private static final JLabel bannerLabel = new JLabel();
  private static final JLabel adLabel = new JLabel();
  private static final JLabel attendanceLabel = new JLabel();

  private SelectMember userInformation;
  private DefaultHeaderUI defaultHeader;
  private static LoginFormUI loginForm;

  public MainFormUI(LoginFormUI loginForm, SelectMember member) {
    this.userInformation = member;
    this.loginForm = loginForm;
    init();
  }

  //UI
  void init() {
    Container cp = getContentPane();
    cp.setLayout(null);
    cp.setBackground(ColorSet.BACKGROUND);

    // 헤더
    defaultHeader = new DefaultHeaderUI("login-main", this, loginForm, userInformation);
    add(defaultHeader);

    //프로필
    ImageIcon manIcon = new ImageIcon("images/icon/man5050.png");
    ImageIcon womanIcon = new ImageIcon("images/icon/woman5050.png");

    if(userInformation.getSex(0) == 0){ //남자
      profileLabel.setIcon(manIcon);
    }else { //여자
      profileLabel.setIcon(womanIcon);
    }
    cp.add(profileLabel);
    profileLabel.setBounds(15, 80, 50, 50);

    JLabel welecomLabel =new JLabel(userInformation.getName()+"학부모님 환영합니다");
    welecomLabel.setBounds(75, 80, 350, 50);

    //광고
    cp.add(adLabel);
    ImageIcon ad1 = new ImageIcon("images/icon/tat4.gif");
    adLabel.setIcon(ad1);
    adLabel.setLocation(100, 210);
    adLabel.setSize(200, 200);
    setUI();

    cp.add(boardButton); //게시판 버튼
    boardButton.setBounds(40, 421, 150, 50);
    boardButton.addMouseListener(new MainEvent(this, loginForm, userInformation));

    cp.add(classButton); //학습현황 버튼
    classButton.setBounds(200, 421, 150, 50);
    classButton.addActionListener(e -> {
      if (userInformation.getMilktId() != null) {
        this.setVisible(false);
        new StatusUI(this, userInformation);
      } else {
        JOptionPane.showMessageDialog(null, "자식을 등록해주세요");
      }
    });

    //배너 위 출서확인 아이콘
    
    

    AttendanceEvent ae = new AttendanceEvent(null, userInformation, null);
    ae.attendanceshow();
    attendanceLabel.setBounds(0, 471,400, 200);
    cp.add(attendanceLabel);

    /*
    JLabel attendanceLabel = new JLabel();
    ImageIcon yesKidsIcon1 = new ImageIcon("images/icon/complete.png");
    ImageIcon yesKidsIcon2 = new ImageIcon("images/icon/rest.png");
    
    if(true){
      attendanceLabel.setIcon(yesKidsIcon1);
    }else{
      attendanceLabel.setIcon(yesKidsIcon2);
    }
    attendanceLabel.setBounds(0, 381,400, 200);
    cp.add(attendanceLabel);
    */

    //배너
    cp.add(bannerLabel);
    ImageIcon banner = new ImageIcon("images/icon/banner.gif");
    bannerLabel.setIcon(banner);
    bannerLabel.setSize(400, 90);
    bannerLabel.setLocation(0, 581);
    setUI();
  }

  public static JLabel getMyLabel(){
    return attendanceLabel;
  }

  //버튼 액션
  void setUI() {
    setTitle("Tspoon");
    setSize(400, 710);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}