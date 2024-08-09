package repository;

import dbutil.MariaConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ReplyRepository {
  private Connection con = MariaConnection.getInstance().getConnection();
  private DefaultTableModel replySet;
  private JTextArea textReply;

  public ReplyRepository(DefaultTableModel replySet) {
    this.replySet = replySet;
  }

  public void selectReply(int boardNo) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sqlReply = "select REPLY_NO,CONTENT,ID,REPLY_DATE from reply join tspoon_member using(tspoon_no) where BOARD_NO = " + boardNo + " and REPLY_YN=0 order by REPLY_DATE";

    try {
      pstmt = con.prepareStatement(sqlReply);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        replySet.addRow(new Object[]{rs.getInt("REPLY_NO"), rs.getString("CONTENT"), rs.getString("ID"), rs.getDate("REPLY_DATE")});
      }
    } catch (SQLException se) {
      se.printStackTrace();
    }
  }

  public int deleteArticle(int boardNo) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "update BOARD set BOARD_YN = 1 where BOARD_NO = " + boardNo;

    int result = 0;
    try {
      pstmt = con.prepareStatement(sql);
      result = pstmt.executeUpdate();

      if (result > 0) con.commit();
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return result;
  }

  public int updateArticle(JTextArea textArea, int boardNo) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String updateText = textArea.getText();
    String sql = "update BOARD set ARTICLE = " + updateText + " where BOARD_NO = " + boardNo;

    int result = 0;
    try {
      pstmt = con.prepareStatement(sql);
      result = pstmt.executeUpdate();

      if (result > 0) con.commit();
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return result;
  }


  public int insertReply(int tspoonNo, int boardNo) {
    PreparedStatement pstmt = null;
    String insertReply = textReply.getText();
    insertReply = "\"" + insertReply + "\"";
    String sql = "insert into REPLY(CONTENT,REPLY_DATE,REPLY_YN,BOARD_NO,TSPOON_NO) values (" + insertReply + ",now(),0," + boardNo + "," + tspoonNo + ")"; // 꼭 수정 요망

    int result = 0;
    try {
      pstmt = con.prepareStatement(sql);
      result = pstmt.executeUpdate();

      if (result > 0) con.commit();
    } catch (SQLException se) {
      se.printStackTrace();
    }

    replySet.setNumRows(0);
    selectReply(boardNo);
    textReply.setText("");

    return result;
  }

  public int deleteReply(int tspoonNo, int replyNo) {
    PreparedStatement pstmt = null;
    String sql = "update REPLY set REPLY_YN = 1 where REPLY_NO=" + replyNo + " and TSPOON_NO=" + tspoonNo;

    int result = 0;
    try {
      pstmt = con.prepareStatement(sql);
      result = pstmt.executeUpdate();

      if (result > 0) con.commit();
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return result;
  }
}
