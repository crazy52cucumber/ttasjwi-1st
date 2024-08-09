package event;

import domain.SelectMember;
import repository.BoardRepository;
import ui.ArticleUI;
import ui.BoardUI;
import ui.WriteUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BoardEvent extends KeyAdapter implements MouseListener {
  private BoardRepository boardRepository;
  private JTextField textSearch;
  private JTable table;
  private DefaultTableModel model;

  private int doubleClick;
  private int firstClick = 0;
  private int secondClick = 0;
  SelectMember member;

  public BoardEvent(SelectMember member) {
    this.member = member;
  }

  public BoardEvent(JTextField textSearch, JTable table, DefaultTableModel model) {
    if (boardRepository == null) {
      this.boardRepository = new BoardRepository();
    }
    this.textSearch = textSearch;
    this.table = table;
    this.model = model;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      model.setNumRows(0);
      List<Object[]> newModel = boardRepository.selectArticle(textSearch.getText());

      for (Object[] o : newModel) {
        model.addRow(o);
      }

      table.setModel(model); // JTable을 검색한 값으로 새로 바꿔서 집어넣는 과정
      table.revalidate();
      table.repaint();
    }

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getSource() instanceof JTable) {
      JTable jt = ((JTable) e.getSource());
      if (isDoubleClick(e)) {
        new ArticleUI(member, (int) jt.getValueAt(jt.getSelectedRow(), 0));
      }
    } else {
      int clickTextBox = e.getClickCount();
      if (clickTextBox >= 1) {
        textSearch.setText("");
        clickTextBox = 0;
      }
    }
  }

  public boolean isDoubleClick(MouseEvent e) {
    boolean flag = false;
    if (doubleClick == 0) {
      firstClick = ((JTable) e.getSource()).getSelectedRow();
    }

    if (doubleClick == 1) {
      secondClick = ((JTable) e.getSource()).getSelectedRow();
      doubleClick++;
    }

    if (doubleClick == 2) {
      if (firstClick == secondClick) {
        flag = true;
      } else {
        secondClick = firstClick;
        doubleClick--;
      }
    }
    doubleClick++;

    if (doubleClick > 2) doubleClick = 0;

    return flag;
  }
}
