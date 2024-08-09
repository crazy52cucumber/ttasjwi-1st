package ui;

import domain.SelectMember;
import event.WriteEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WriteUI extends JFrame {
  private JPanel contentPane;
  private JTextField articleTitle;
  private JTextArea articleDetail;
  private JComboBox<String> tagBox;
  private String tagName[] = {"자유", "질문", "정보", "수업", "공지"};


  private SelectMember member;

  public WriteUI(SelectMember member) {
    this.member = member;
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);
    articleTitle = new JTextField();
    articleTitle.setBounds(124, 85, 248, 30);
    contentPane.add(articleTitle);
    articleTitle.setColumns(10);

    articleDetail = new JTextArea();
    articleDetail.setBounds(12, 145, 360, 380);
    contentPane.add(articleDetail);

    // 게시글 등록 버튼
    JButton registerArticle = new JButton("게시글 등록");
    registerArticle.setBounds(253, 543, 119, 39);
    contentPane.add(registerArticle);
    registerArticle.addMouseListener(new WriteEvent(this, member, registerArticle, articleTitle, articleDetail));

    tagBox = new JComboBox<String>(tagName);
    tagBox.setBounds(12, 85, 100, 30);
    contentPane.add(tagBox);

    setUI();
  }

  private void setUI() {
    setResizable(false);
    setVisible(true);

    setBounds(100, 100, 400, 710);
    setLocationRelativeTo(null);
  }

}
