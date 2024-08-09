package event;

import domain.SelectMember;
import domain.Study;
import repository.StatusRepository;
import ui.StatusUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatusEvent implements ActionListener {
  private StatusUI statusUI;
  private StatusRepository statusRepository;
  public List<Study> result;
  private int no1, no2, no3, no4;
  private SelectMember member;

  public StatusEvent(StatusUI statusUI, SelectMember member, StatusRepository statusRepository) {
    this.statusUI = statusUI;
    this.member = member;
    this.statusRepository = statusRepository;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox box = (JComboBox) e.getSource();
    if (box == statusUI.box1) {
      no1 = box.getSelectedIndex();
    } else if (box == statusUI.box2) {
      no2 = box.getSelectedIndex();
    } else if (box == statusUI.box3) {
      no3 = box.getSelectedIndex();
    } else if (box == statusUI.box4) {
      no4 = box.getSelectedIndex();
    }
    if (statusUI.idx == 0) {
      result = statusRepository.select(member.getMilktId(), statusUI.sub[no1], statusUI.desc[no2], statusUI.idx);
      statusUI.updateUI(result);
    } else if (statusUI.idx == 1) {
      result = statusRepository.select(member.getMilktId(), statusUI.sub[no3], statusUI.desc[no4], statusUI.idx);
      statusUI.updateUI2(result);
    }
  }
}