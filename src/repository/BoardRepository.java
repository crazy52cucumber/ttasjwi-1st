package repository;

import dbutil.MariaConnection;
import domain.InsertBoard;
import domain.SelectBoard;
import domain.SelectMember;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
  private Connection con = MariaConnection.getInstance().getConnection();

  // 검색 관련
  public List<Object[]> selectArticle(String title) {
    PreparedStatement pstmt;
    ResultSet rs;
    ResultSetMetaData rsmd;

    List<Object[]> li = new ArrayList<>();

    String sql = "select board_no,title,id,board_date from board join tspoon_member using(tspoon_no) where article like '%" + title + "%' and board_yn = 0 order by board_no desc";

    try {
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        li.add(new Object[]{rs.getInt("BOARD_NO"), rs.getString("TITLE"), rs.getString("id"), rs.getDate("BOARD_DATE")});
      }

    } catch (SQLException se) {
      se.printStackTrace();
    }

    return li;
  }

  public void selectAllArticle(DefaultTableModel model) {
    String sql = "select board_no,title,article,id,board_date from board join tspoon_member using(tspoon_no) where board_yn = 0 order by board_no desc";
    PreparedStatement pstmt;
    ResultSet rs;
    try {
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        model.addRow(new Object[]{rs.getInt("BOARD_NO"), rs.getString("TITLE"), rs.getString("id"), rs.getDate("BOARD_DATE")});
      }
    } catch (SQLException se) {
      se.printStackTrace();
    }
  }

  // 글쓰기
  public void insertArticle(InsertBoard board) {
    PreparedStatement pstmt = null;

    String title = "\"" + board.getTitle() + "\"";
    String detail = "\"" + board.getArticle() + "\"";
    String sql = "insert into BOARD(TITLE,ARTICLE,BOARD_DATE,BOARD_YN,TSPOON_NO) values (" + title + "," + detail + ",now(),0," + board.getTspoonNo() + ")";
    System.out.println(" insert문 >" + sql);
    int result = 0;
    try {
      pstmt = con.prepareStatement(sql);
      result = pstmt.executeUpdate();

      if (result > 0) {
        con.commit();
        System.out.println("글쓰기 성공");
      }
    } catch (SQLException se) {
      se.printStackTrace();
    }
  }

  // 상세글 보기
  public SelectBoard showArticle(int boardNo) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select * from BOARD where board_no = ?";

    SelectBoard board = null;
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, boardNo);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        board = new SelectBoard(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6));
      }
    } catch (SQLException se) {
      se.printStackTrace();
    }

    return board;
  }
}
