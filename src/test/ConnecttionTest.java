package test;

import dbutil.MariaConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnecttionTest {
  static Connection con = MariaConnection.getInstance().getConnection();

  public static void main(String[] args) {
    //int r = insertMember("aq");
    System.out.println(getLastAutoincrement() + " 음 ..");
  }


  private static int getLastAutoincrement() {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "SELECT * FROM test ORDER BY 1 DESC limit 2";
    int i = 0;
    try {
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();
      rs.last();
      i = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return i;
  }

  private static int insertMember(String name) {
    String sql = "insert into test(name) values (?)";
    int result = 0;
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, name);
      result = pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("아이디 중복");
    }
    return result;
  }
}
