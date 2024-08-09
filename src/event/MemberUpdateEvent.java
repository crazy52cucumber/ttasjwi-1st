package event;

import dbutil.MariaConnection;
import domain.SelectMember;
import domain.dto.UpdateMember;
import repository.MemberRepository;
import ui.MemberUpdateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class MemberUpdateEvent implements ActionListener {
  private MemberRepository memberRepository;
  private SelectMember member;
  private MemberUpdateUI memberUpdate;
  private List<JTextField> textFieldList;

  public MemberUpdateEvent(MemberUpdateUI memberUpdate, SelectMember member, List<JTextField> textFieldList) {
    this.memberUpdate = memberUpdate;
    this.member = member;
    this.textFieldList = textFieldList;
    memberRepository = new MemberRepository();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String getPassword = textFieldList.get(0).getText();
    String getPhoneNum = textFieldList.get(1).getText();
    String getAddress = textFieldList.get(2).getText();

    int result = memberRepository.updateMember(new UpdateMember(member.getId(), getPassword, getPhoneNum, getAddress));
    if (result == 1) {
      if (!getPhoneNum.isBlank()) member.setPhoneNum(getPhoneNum);
      if (!getAddress.isBlank()) member.setAddress(getAddress);
      memberUpdate.setVisible(false);
      System.out.println("수정 완료");
    }
  }
}




