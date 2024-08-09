package repository;

import dbutil.MariaConnection;
import domain.InsertMember;
import domain.SelectMember;
import domain.dto.SelectMilktMember;
import domain.dto.UpdateMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class MemberRepository {
  public static ConcurrentHashMap<String, SelectMember> members = new ConcurrentHashMap<>();
  private Connection con = MariaConnection.getInstance().getConnection();
  private static final Logger logger = Logger.getLogger(MemberRepository.class.getName());

  private static final String INFOLOGGER = "Database connection established";

  public SelectMember selectMember(String userId) {
    logger.info(INFOLOGGER);
    SelectMember selectMember = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "select TSPOON_NO, ID, NAME, PHONE_NUM, ADDRESS, JOIN_DATE, SEX from tspoon_member where ID=?";
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, userId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int tspoonNo = rs.getInt("TSPOON_NO");
        String id = rs.getString("ID");
        String name = rs.getString("NAME");
        String phoneNum = rs.getString("PHONE_NUM");
        String address = rs.getString("ADDRESS");
        Date joinDate = rs.getDate("JOIN_DATE");
        Integer sex = rs.getInt("SEX");

        selectMember = new SelectMember(tspoonNo, id, name, phoneNum, address, joinDate, sex);
        Optional<SelectMilktMember> selectMilktMember = checkMilktMember(tspoonNo);

        if (!selectMilktMember.isEmpty()) {
          selectMember.setMilktId(selectMilktMember.get().getMilktid());
        }
      } else {
        logger.severe("일치하는 회원이 없습니다.");
      }
    } catch (SQLException se) {
      logger.severe("Error executing SQL query" + se.getMessage());
    } finally {
      if (rs != null) {
        MariaConnection.closeAll(pstmt, rs);
      }
    }
    return selectMember;
  }

  public char[] selectUserPassword(String id) {
    logger.info(INFOLOGGER);
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    char[] userPassword = {};
    try {
      pstmt = con.prepareStatement("select PASSWORD from TSPOON_MEMBER where ID=?");
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        String pwd = rs.getString("password");
        userPassword = pwd.toCharArray();
      }
    } catch (SQLException se) {
      logger.severe("Error executing SQL query" + se.getMessage());
    } finally {
      if (rs != null) {
        MariaConnection.closeAll(pstmt, rs);
      }
    }
    return userPassword;
  }

  public int insertMember(InsertMember insertMember) {
    String sql = "insert into tspoon_member(ID, PASSWORD, NAME, PHONE_NUM, ADDRESS, JOIN_DATE, SEX, ROLE) values (?,?,?,?,?,now(), ?, 1)";
    int result = 0;
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, insertMember.getId());
      pstmt.setString(2, insertMember.getPassword());
      pstmt.setString(3, insertMember.getName());
      pstmt.setString(4, insertMember.getPhone_num());
      pstmt.setString(5, insertMember.getAddress());
      pstmt.setInt(6, insertMember.getSex());

      result = pstmt.executeUpdate();
      if (result > 0) con.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("아이디 중복");
    }
    return result;
  }

  public Optional<SelectMilktMember> checkMilktMember(int tspoonNo) {
    String sql = "select tm.NAME 부모,  mm.NAME 자녀 ,mm.MILKTID 자녀아이디 from\n" +
            "tspoon_member tm join milk_member mm on tm.TSPOON_NO = mm.TSPOON_NO where tm.TSPOON_NO = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    SelectMilktMember selectMilktMember = null;
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, tspoonNo);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        String tspoonMemberName = rs.getString("부모");
        String milktMemberName = rs.getString("자녀");
        String milktid = rs.getString("자녀아이디");
        selectMilktMember = new SelectMilktMember(tspoonMemberName, milktMemberName, milktid);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(selectMilktMember);
  }


  public int updateMember(UpdateMember updateMember) {
    PreparedStatement pstmt = null;
    int result = 0;
    StringBuilder sql = new StringBuilder();
    sql.append("update tspoon_member set ");

    String updatePassword = updateMember.getPassword();
    String updatePhoneNum = updateMember.getPhoneNum();
    String updateAddress = updateMember.getAddress();

    if (!updatePassword.isBlank()) {
      sql.append("PASSWORD = \'");
      sql.append(updatePassword);
      sql.append("\'");
      if (!updatePhoneNum.isBlank()) sql.append(",  ");
    }


    if (!updatePhoneNum.isBlank()) {
      sql.append("PHONE_NUM = \'");
      sql.append(updatePhoneNum);
      sql.append("\'");
      if (!updateAddress.isBlank()) sql.append(",  ");
    }


    if (!updateAddress.isBlank()) {
      sql.append("ADDRESS = \'");
      sql.append(updateAddress);
      sql.append("\' ");
    }

    sql.append("where ID = \'");
    sql.append(updateMember.getId());
    sql.append("\'");

    System.out.println("update query > " + sql.toString());
    try {
      pstmt = con.prepareStatement(sql.toString());
      result = pstmt.executeUpdate();

      if (result > 0) {
        con.commit();
      }
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return result;
  }

  private int getComma(UpdateMember updateMember) {
    int i = 0;

    if (updateMember.getPassword().isBlank()) i++;
    if (updateMember.getPhoneNum().isBlank()) i++;
    if (updateMember.getAddress().isBlank()) i++;

    return i;
  }
}
