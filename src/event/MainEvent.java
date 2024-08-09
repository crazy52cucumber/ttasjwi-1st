package event;

import domain.SelectMember;
import ui.BoardUI;
import ui.LoginFormUI;
import ui.MainFormUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainEvent implements MouseListener {
  private MainFormUI currentObj;
  private LoginFormUI previousObj;
  private SelectMember member;

  public MainEvent(MainFormUI mainFormUI, LoginFormUI loginFormUI, SelectMember member) {
    this.currentObj = mainFormUI;
    this.previousObj = loginFormUI;
    this.member = member;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    currentObj.setVisible(false);
    new BoardUI(currentObj, previousObj, member);
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}