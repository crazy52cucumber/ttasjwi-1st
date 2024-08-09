package event;

import dbutil.MariaConnection;
import domain.SelectMember;
import ui.MemberUpdateUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberInfoEvent implements ActionListener {
  private Connection con = MariaConnection.getInstance().getConnection();

  private SelectMember member;
  private String jbName;


  public MemberInfoEvent(String jbName, SelectMember member) {
    this.jbName = jbName;
    this.member = member;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ResultSet rs;
    String sql1 = "SELECT COUNT(*) FROM MILK_MEMBER WHERE MILKTID = ?";
    String sql2 = "UPDATE MILK_MEMBER SET TSPOON_NO = ? WHERE MILKTID = ?";

    if (jbName.equals("bChildUpdate")) {
      String userInput = JOptionPane.showInputDialog(null, "자녀의 ID를 입력해주세요", "자녀 추가", JOptionPane.PLAIN_MESSAGE);
      if (userInput != null) { //입력값이 있을 떄
        try (PreparedStatement pstmt1 = con.prepareStatement(sql1)) {
          pstmt1.setString(1, userInput);
          rs = pstmt1.executeQuery();
          if (rs.next()) { //해당하는 자녀가 있으면
            int count = rs.getInt(1);
            if (count > 0) {
              System.out.println("자녀를 찾았습니다");
              //추가 작업 (MILKMEMBER 테이블에 부모의 ID 입력)
              try (PreparedStatement pstmt2 = con.prepareStatement(sql2)) {
                pstmt2.setInt(1, member.getTspoonNo());
                pstmt2.setString(2, userInput);
                int rowsInserted = pstmt2.executeUpdate();
                if (rowsInserted > 0) {
                  con.commit();
                  System.out.println("자녀ID와 연결되었습니다");
                  JOptionPane.showMessageDialog(null, "자녀ID와 연결되었습니다", "자녀 추가에 성공하였습니다", JOptionPane.PLAIN_MESSAGE);
                } else {
                  System.out.println("부모의 아이디를 찾지 못했습니다");
                }
              } catch (SQLException se) {
                se.printStackTrace();
                System.out.println("MILK_MEMBER에 TSPOON_NO 입력 실패");
              }
            } else { //해당하는 자녀가 없으면
              System.out.println("해당하는 MILKTID의 회원이 없습니다.");
              JOptionPane.showMessageDialog(null, "일치하는 ID가 존재하지 않습니다", "자녀 추가에 실패했습니다", JOptionPane.ERROR_MESSAGE);
            }
          }
        } catch (SQLException se) {
          se.printStackTrace();
        }

      } else { //입력값이 없을 때 or 창을 닫을 때
        JOptionPane.showMessageDialog(null, "자녀를 추가하지 않습니다");
      }
    } else if (jbName.equals("bMemberUpdate")) {
      new MemberUpdateUI(member).init();
    }
  }
}