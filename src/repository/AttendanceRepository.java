package repository;
import dbutil.MariaConnection;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import domain.SelectMember;
import java.sql.*;

public class AttendanceRepository {
    private Connection con = MariaConnection.getInstance().getConnection(); 
    private SelectMember member;
 
    public AttendanceRepository (SelectMember member){
      this.member = member;
    }

    public Date getAttendanceDate (){
        PreparedStatement pstmt;
        ResultSet rs = null;
        Date attendanceDate = null;
        String AD = null;
        
        String sql = "SELECT MYCLASS_END_DATE FROM MYCLASS c JOIN MILK_MEMBER m ON c.MILKTID = m.MILKTID WHERE TSPOON_NO = ?";
        try {
          pstmt = con.prepareStatement(sql);
          pstmt.setInt(1, member.getTspoonNo());
          rs = pstmt.executeQuery();
          while (rs.next()) {
            attendanceDate = rs.getDate(1);
          }
          System.out.println("마지막출석일 출력");
        }catch(SQLException se){
          se.printStackTrace();
        }finally{
          try {
              rs.close();
          } catch (SQLException se) {
            se.printStackTrace();
          }
        }
        return attendanceDate;
      }
    }