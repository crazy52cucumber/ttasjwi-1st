package event;

import dbutil.MariaConnection;
import domain.InsertBoard;
import domain.SelectMember;
import repository.BoardRepository;
import ui.WriteUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class WriteEvent extends MouseAdapter {
  private BoardRepository boardRepository;
  private JButton button;
  private JTextField articleTitle;
  private JTextArea articleDetail;
  private SelectMember member;
  private WriteUI writeUI;

  public WriteEvent(SelectMember member, JButton button) {
    this.member = member;
    this.button = button;
  }

  public WriteEvent(WriteUI writeUI, SelectMember member, JButton button, JTextField articleTitle, JTextArea articleDetail) {
    this.writeUI = writeUI;
    this.member = member;
    this.button = button;
    this.articleTitle = articleTitle;
    this.articleDetail = articleDetail;
    boardRepository = new BoardRepository();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    String btnType = ((JButton) e.getSource()).getText();
    if (btnType.contains("글쓰기")) {
      new WriteUI(member);
    }

    if (btnType.contains("등록")) {
      int check = JOptionPane.showConfirmDialog(null, "등록하시겠습니까?", "글 올리기", JOptionPane.YES_NO_OPTION);
      if (check == JOptionPane.YES_OPTION) {
        String title = articleTitle.getText();
        String article = articleDetail.getText();
        if (title.isBlank() || article.isBlank()) {
          JOptionPane.showMessageDialog(null, "제목과 내용이 비어있으면 안됩니다.", "다시 입력하세요", JOptionPane.WARNING_MESSAGE);
          return;
        } else {
          boardRepository.insertArticle(new InsertBoard(title, article, 0, member.getTspoonNo()));
          JOptionPane.showMessageDialog(null, "등록되었습니다");
          writeUI.setVisible(false);
        }
      }

    }

  }
}
