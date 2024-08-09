package ui;

import dbutil.MariaConnection;
import domain.SelectBoard;
import domain.SelectMember;
import event.ArticleEvent;
import repository.BoardRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ArticleUI extends JFrame {
  private static final long serialVersionUID = 1L;

  Connection con = MariaConnection.getInstance().getConnection();                  // 여기서부터는 BOARD와 중복되는거라 나중에 지워도 괜찮습니다 pstmt만 다르게 해놨음
  PreparedStatement pstmt3, pstmt4, pstmtDel, pstmtUpd, pstmtInsRe, pstmtDelReply;
  ResultSet rsReply, rsArticle;
  ResultSetMetaData rsmd;
  int replyNoData = -1;


  //// GUI관련 선언부
  String getArticle;
  private DefaultTableModel replySet;
  private JPanel contentPane;
  private JTable replyCollection;
  private JTextArea textArticle, textReply;
  private String colReply[] = {"번호", "댓글", "ID", "등록일"};

  int boardNo;
  SelectMember member;

  @SuppressWarnings("serial")
  public ArticleUI(SelectMember member, int boardNo) {
    this.member = member;
    this.boardNo = boardNo;
    replySet = new DefaultTableModel(colReply, 0) {
      public boolean isCellEditable(int r, int c) {
        return (c == 5) ? true : false;
      }
    };
    setVisible(true);


    replyCollection = new JTable(replySet);
    replyCollection.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        TableModel replySetTM = replyCollection.getModel();
        int replyRow = replyCollection.getSelectedRow();
        //System.out.print(replyRow);

        replyNoData = (int) replySetTM.getValueAt(replyRow, 0);
        int choiceDelete = -1;
        choiceDelete = JOptionPane.showConfirmDialog(null, "댓글을 삭제하시겠습니까?", "댓글 삭제?", JOptionPane.YES_OPTION);
        if (choiceDelete == JOptionPane.YES_OPTION) {
          deleteReply();
          JOptionPane.showMessageDialog(null, "삭제되었습니다", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
          replySet.setNumRows(0);
          selectReply();
        }


      }
    });

    replyCollection.setBackground(new Color(255, 255, 204));
    replyCollection.getColumn("번호").setPreferredWidth(0);
    replyCollection.getColumn("댓글").setPreferredWidth(200);
    replyCollection.getColumn("ID").setPreferredWidth(20);
    replyCollection.getColumn("등록일").setPreferredWidth(70);
    setResizable(false);
    setBounds(100, 100, 400, 711);

    contentPane = new JPanel();
    setContentPane(contentPane);
    contentPane.setLayout(null);


    JScrollPane scrollPane2 = new JScrollPane();
    scrollPane2.setFont(new Font("굴림", Font.BOLD, 16));
    scrollPane2.setBounds(12, 293, 360, 300);
    contentPane.add(scrollPane2);

    scrollPane2.setViewportView(replyCollection);

    textArticle = new JTextArea();
    textArticle.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == textArticle) {
          textArticle.setEditable(true);
        }
      }
    });
    textArticle.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 12));
    textArticle.setSelectionColor(SystemColor.textHighlight);

    textArticle.append(getArticle);
    textArticle.setEnabled(true);
    textArticle.setEditable(false);
    textArticle.setBounds(12, 38, 360, 184);
    contentPane.add(textArticle);

    textReply = new JTextArea();
    textReply.setBounds(12, 604, 251, 24);
    textReply.setLineWrap(true);
    contentPane.add(textReply);

/////////////////////////////버튼 시작////////////////////

    JButton insertReplyButton = new JButton("댓글달기");
    insertReplyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == insertReplyButton) {
          insertReply();
        }
      }
    });
    insertReplyButton.setBounds(275, 605, 97, 23);
    contentPane.add(insertReplyButton);


    int tspoonNo2 = checkOwner(boardNo);

    if (member.getTspoonNo() == tspoonNo2) {
      JButton deleteButton = new JButton("삭제"); // 로그인 비교해서 button의 setVisible의 값을 바꿔야함
      deleteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Object obj = e.getSource();
          int choice = -1;
          if (obj == deleteButton) {
            choice = JOptionPane.showConfirmDialog(deleteButton, "작성글을 지우시겠습니까?", "작성글 삭제?", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
              deleteArticle(); //다음으로 게시판으로 돌아가는 로직 필요
              setVisible(false);
            }
          }
        }
      });
      deleteButton.setBounds(166, 233, 97, 23);
      contentPane.add(deleteButton);


      JButton updateButton = new JButton("수정");
      updateButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Object obj = e.getSource();
          if (obj == updateButton) {
            updateArticle();
          }
        }
      });
      updateButton.setHorizontalTextPosition(SwingConstants.LEFT);
      updateButton.setBounds(275, 233, 97, 23);
      contentPane.add(updateButton);
    }

    showArticle();
    selectReply();

  }

  private int checkOwner(int boardNo) {
    String sql = "select tspoon_no from board where board_no = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    int tspoonNo = 0;
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, boardNo);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        tspoonNo = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tspoonNo;

  }


  void showArticle() {

    String sqlArticle = "select ARTICLE,BOARD_NO from BOARD where board_no = ?"; //where board no = x 로 비교

    try {
      pstmt3 = con.prepareStatement(sqlArticle);
      pstmt3.setInt(1, boardNo);
      rsArticle = pstmt3.executeQuery();

      while (rsArticle.next()) {
        getArticle = rsArticle.getString("ARTICLE");
        boardNo = rsArticle.getInt("BOARD_NO");
      }
    } catch (SQLException se) {
    } catch (NullPointerException npe) {
    }
    textArticle.setText(getArticle);
  }

  void selectReply() { // 번호, 댓글내용, 아이디 (일단 전체 댓글)

    String sqlReply = "select REPLY_NO,CONTENT,ID,REPLY_DATE from reply join tspoon_member using(tspoon_no) where BOARD_NO = " + boardNo + " and REPLY_YN=0 order by REPLY_DATE"; //using 뒤에 board_no = x 로 비교
    try {
      pstmt4 = con.prepareStatement(sqlReply);
      rsReply = pstmt4.executeQuery();
      rsmd = rsReply.getMetaData();

      while (rsReply.next()) {
        replySet.addRow(new Object[]{rsReply.getInt("REPLY_NO"), rsReply.getString("CONTENT"), rsReply.getString("ID"), rsReply.getDate("REPLY_DATE")});
      }
    } catch (SQLException se) {
    }

  }

  void deleteArticle() {
    String sqlDel = "update BOARD set BOARD_YN = 1 where BOARD_NO = " + boardNo;
    System.out.println("delete query > " + sqlDel);

    try {
      pstmtDel = con.prepareStatement(sqlDel);
      int result = pstmtDel.executeUpdate();

      if (result > 0) con.commit();
      System.out.println("삭제 성공");
    } catch (SQLException se) {
      System.out.println("삭제 실패");
    }

  }

  void updateArticle() {

    String updateText = "";
    updateText = textArticle.getText();
    updateText = "\"" + updateText + "\"";

    String sqlUp = "update BOARD set ARTICLE = " + updateText + " where BOARD_NO = " + boardNo;
    try {
      pstmtUpd = con.prepareStatement(sqlUp);
      int result = pstmtUpd.executeUpdate();

      if (result > 0) con.commit();
    } catch (SQLException se) {
    }

    showArticle();
  }

  void insertReply() {
    String insertReply = "";
    insertReply = textReply.getText();
    insertReply = "\"" + insertReply + "\"";

    String sqlInReply = "insert into REPLY(CONTENT,REPLY_DATE,REPLY_YN,BOARD_NO,TSPOON_NO) values (" + insertReply + ",now(),0," + boardNo + "," + member.getTspoonNo() + ")"; // 꼭 수정 요망
    try {
      pstmtInsRe = con.prepareStatement(sqlInReply);
      int result = pstmtInsRe.executeUpdate();
      if (result > 0) con.commit();
    } catch (SQLException se) {
    }

    replySet.setNumRows(0);

    selectReply();

    textReply.setText("");
  }

  void deleteReply() {
    String deleteReSql = "update REPLY set REPLY_YN = 1 where REPLY_NO=" + replyNoData + " and TSPOON_NO=" + member.getTspoonNo();

    try {
      pstmtDelReply = con.prepareStatement(deleteReSql);
      int result = pstmtDelReply.executeUpdate();

      if (result > 0) con.commit();
    } catch (SQLException se) {
    }
  }
}
