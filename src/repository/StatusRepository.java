package repository;

import dbutil.MariaConnection;
import domain.Study;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository {
  private Connection con = MariaConnection.getInstance().getConnection();

  String sql = "SELECT x.name, x.grade, x.teacher, x.subject, x.class_name, x.progress\n"
          + "\t\t,x.class_count, x.progress_rate, x.start_date, x.end_date\n"
          + "from(\n"
          + "SELECT mm.name name\n"
          + "    ,cl.grade grade\n"
          + "    ,cl.teacher teacher\n"
          + "    ,cl.subject subject\n"
          + "    ,cl.class_name class_name\n"
          + "    ,max(ml.progress) progress\n"
          + "    ,cl.class_count class_count\n"
          + "    ,truncate(max(ml.progress)/cl.class_count,2) *100 progress_rate\n"
          + "    ,mc.myclass_start_date start_date\n"
          + "    ,mc.myclass_end_date end_date\n"
          + "    ,mc.myclass_yn myclass_yn\n"
          + "    ,mc.complete complete\n"
          + "  FROM milk_member mm\n"
          + "  JOIN myclass mc\n"
          + "    ON mm.milktid = mc.milktid\n"
          + "  JOIN class_list cl\n"
          + "    ON cl.class_no = mc.class_no\n"
          + "  JOIN myclass_log ml\n"
          + "    ON ml.myclass_no = mc.MYCLASS_NO\n"
          + " WHERE 1=1\n"
          + "   AND mc.milktid = ?\n"
          + "  GROUP BY ml.myclass_no)x\n"
          + "WHERE 1=1";


  String sql2 = sql;

  public void addOption(String sub, String desc, int idx) {
    sql = sql2;
    if (idx == 0) {
      sql += " and x.myclass_yn =0"
              + " and x.complete =0";
    } else if (idx == 1) {
      sql += " and x.complete =1";
    }
    if ("국어".equals(sub)) {
      sql += " and x.subject ='국어'";
    } else if ("수학".equals(sub)) {
      sql += " and x.subject ='수학'";
    } else if ("영어".equals(sub)) {
      sql += " and x.subject ='영어'";
    } else if ("과학".equals(sub)) {
      sql += " and x.subject ='과학'";
    } else if ("사회".equals(sub)) {
      sql += " and x.subject ='사회'";
    }
    if ("최근 수강순".equals(desc)) {
      sql += " order by x.end_date desc";
    } else if ("오래된 수강순".equals(desc)) {
      sql += " order by x.end_date";
    }
  }

  public String findChildName(int tspoonNo) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select mm.name from milk_member mm left join tspoon_member tm on mm.TSPOON_NO = tm.TSPOON_NO where tm.TSPOON_NO = ? ";

    String result = "";
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, tspoonNo);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        result = rs.getString(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public List<Study> select(String milkid, String box1, String box2, int idx) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    addOption(box1, box2, idx);
    List<Study> al = new ArrayList();
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, milkid);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String name = rs.getString(1);
        String grade = rs.getString(2);
        String teacher = rs.getString(3);
        String subject = rs.getString(4);
        String cname = rs.getString(5);
        int mcnt = rs.getInt(6);
        int ccnt = rs.getInt(7);
        int progress = rs.getInt(8);
        Date sdate = rs.getDate(9);
        Date edate = rs.getDate(10);
        al.add(new Study(name, grade, teacher, subject, cname, mcnt, ccnt, progress, sdate, edate));
      }
    } catch (SQLException se) {
      System.out.println("읽기 실패");
    }
    return al;
  }
}