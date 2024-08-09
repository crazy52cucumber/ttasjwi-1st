package event;

import domain.SelectMember;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BackEvent extends MouseAdapter {
  private Object currentObj;
  private Object previousObj;

  public BackEvent(Object currentObj, Object previousObj) {
    this.currentObj = currentObj;
    this.previousObj = previousObj;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    ((JFrame) currentObj).setVisible(false);
    ((JFrame) previousObj).setVisible(true);
  }
}
