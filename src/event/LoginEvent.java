package event;

import javax.swing.*;
import java.awt.event.*;

public class LoginEvent extends KeyAdapter {
  private JButton loginButton;

  public LoginEvent(JButton loginButton) {
    this.loginButton = loginButton;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      loginButton.doClick();
    }
  }
}
