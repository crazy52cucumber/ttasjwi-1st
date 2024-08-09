package ui;

import domain.SelectMember;
import event.LoginEvent;
import repository.MemberRepository;
import style.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.logging.Logger;

public class LoginFormUI extends JFrame {

  private static final Logger logger = Logger.getLogger(LoginFormUI.class.getName());
  private MemberRepository memberRepository;
  //id, pwd 입력
  private static final JLabel idLabel = new JLabel("ID : ");
  private JTextField idField;
  private static final JLabel pwdLabel = new JLabel("PWD : ");
  private JPasswordField pwdField;
  //로그인,회원가입 버튼
  static final JButton loginButton = new JButton("Login");
  private static final JButton registerButton = new JButton("Register");

  private DefaultHeaderUI defaultHeaderUI;

  public LoginFormUI() {

    // 폰트
    UIManager.put("Label.font", new Font("KCC-간판체", Font.PLAIN, 12));
    UIManager.put("Button.font", new Font("KCC-간판체", Font.PLAIN, 12));
    UIManager.put("Table.font", new Font("KCC-간판체", Font.PLAIN, 12));
    UIManager.put("TextField.font", new Font("KCC-간판체", Font.PLAIN, 12));
    UIManager.put("TextArea.font", new Font("KCC-간판체", Font.PLAIN, 12));
    UIManager.put("TableHeader.font", new Font("KCC-간판체", Font.PLAIN, 12));
    UIManager.put("TableHeader.font", new Font("KCC-간판체", Font.PLAIN, 12));

    // 헤더
    memberRepository = new MemberRepository();
    init();
  }


  void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  //로그인
  private boolean isPasswordMatch(char[] enteredPassword, char[] storedPassword) {
    try {
      return Arrays.equals(storedPassword, enteredPassword);
    } finally {
      Arrays.fill(enteredPassword, '\0');
      Arrays.fill(storedPassword, '\0');
    }
  }

  void login() {
    String userId = idField.getText();
    SelectMember userInformation = memberRepository.selectMember(userId);
    char[] enteredPassword = pwdField.getPassword();
    char[] storedPassword = memberRepository.selectUserPassword(userId);
    if (userId.isEmpty() || storedPassword == null || enteredPassword.length == 0) {
      showMessage("Login Failed");
      logger.info("storedPassword is empty or enteredPassword is empty");
      return;
    }
    if (isPasswordMatch(enteredPassword, storedPassword)) {
      showMessage("Login Successful");
      MainFormUI mainFormUI = new MainFormUI(this, userInformation);
      idField.setText("");
      pwdField.setText("");
      idField.requestFocus();
      mainFormUI.setVisible(true); // MainForm 초기화
      setVisible(false);
    } else {
      showMessage("Login Failed");
    }
  }

  KeyAdapter enterKeyListener() {
    return new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          loginButton.doClick();
        }
      }
    };
  }

  void init() {

    idField = new JTextField(10);
    pwdField = new JPasswordField(10);
    Container cp = getContentPane();
    cp.setLayout(null);

    // 헤더
    defaultHeaderUI = new DefaultHeaderUI("first", this, null, null);
    add(defaultHeaderUI);

    defaultHeaderUI.setBackground(ColorSet.HEADER);
    cp.setBackground(ColorSet.BACKGROUND);

    //버튼, 입력
    cp.add(idLabel);
    idLabel.setBounds(80, 285, 70, 30);
    //idLabel.setFont(new Font("KCC-간판체", Font.PLAIN, 15));

    registerButton.setBackground(new Color(255, 255, 255));
    loginButton.setBackground(new Color(255, 255, 255));

    cp.add(idField);
    idField.setBounds(160, 285, 160, 30);
    idField.addKeyListener(new LoginEvent(loginButton));

    cp.add(pwdLabel);
    pwdLabel.setBounds(80, 330, 70, 30);
    //pwdLabel.setFont(new Font("KCC-간판체", Font.PLAIN, 15));

    cp.add(pwdField);
    pwdField.setBounds(160, 330, 160, 30);
    pwdField.addKeyListener(enterKeyListener());

    cp.add(loginButton);
    loginButton.setBounds(80, 375, 115, 30);
    loginButton.addActionListener(e -> login());
    loginButton.setFont(new Font("KCC-간판체", Font.PLAIN, 15));

    registerButton.setFont(new Font("KCC-간판체", Font.PLAIN, 15));
    cp.add(registerButton);
    registerButton.setBounds(205, 375, 115, 30);
    registerButton.addActionListener(e -> {
      new MemberRegisterUI(this);
      setVisible(false);
    });

    setUI();
  }

  void setUI() {
    setTitle("Tspoon");
    setSize(400, 710);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
