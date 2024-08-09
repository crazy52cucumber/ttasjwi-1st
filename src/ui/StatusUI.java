package ui;

import domain.SelectMember;
import domain.Study;
import event.StatusEvent;
import repository.StatusRepository;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class StatusUI extends JFrame {
  private Container cp;
  private JTabbedPane tab;
  public JPanel pane, pane2, p1, pa, pb, p2, pc, pd;
  public JComboBox box1, box2, box3, box4;
  public JButton btn;
  public String[] sub = {"영역 전체", "국어", "수학", "영어", "과학", "사회"};
  public String[] desc = {"최근 수강순", "오래된 수강순"};
  private JLabel name, label1, label2, label3, label4, label5, label6, label7,
          name2, label8, label9, label10, label11, label12, label13;
  public int idx;

  private MainFormUI mainFormUI;
  private SelectMember member;

  private Color color = new Color(235, 241, 222);
  private Color color2 = new Color(214, 231, 218);
  private Color color3 = new Color(191, 209, 163);
  private Color color4 = new Color(120, 180, 223);
  private Color color5 = new Color(218, 135, 203);
  private Color color6 = new Color(194, 224, 227);
  private StatusRepository statusRepository;
  private StatusEvent statusEvent;

  public StatusUI(MainFormUI mainFormUI, SelectMember member) {
    this.mainFormUI = mainFormUI;
    this.member = member;
    this.statusRepository = new StatusRepository();
    this.statusEvent = new StatusEvent(this, member, statusRepository);
    init();
  }

  void init() {
    cp = getContentPane();
    tab = new JTabbedPane();
    pane = new JPanel(new BorderLayout());
    pane2 = new JPanel(new BorderLayout());
    p1 = new JPanel(new GridLayout(6, 1));
    p2 = new JPanel(new GridLayout(6, 2)); //Tab2 전체 레이아웃(수정전)
    pa = new JPanel();
    pc = new JPanel();
    box1 = new JComboBox(sub);
    box2 = new JComboBox(desc);
    box3 = new JComboBox(sub);
    box4 = new JComboBox(desc);

    // 무조건 자식 찾기
    String childName = statusRepository.findChildName(member.getTspoonNo());
    if (childName != null) {
      name = new JLabel(childName);
      name2 = new JLabel(childName);
    } else {

    }

    tab.setBorder(BorderFactory.createEmptyBorder(65, 0, 0, 0));

    // 헤더
    DefaultHeaderUI defaultHeaderUI = new DefaultHeaderUI("login", this, mainFormUI, member);
    add(defaultHeaderUI);

    tab.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        idx = tab.getSelectedIndex();
      }
    });

    if (statusEvent.result == null) {
      List<Study> select = statusRepository.select(member.getMilktId(), sub[0], desc[0], idx);
      statusEvent.result = select;
    }

    box1.addActionListener(statusEvent);
    box2.addActionListener(statusEvent);
    box3.addActionListener(statusEvent);
    box4.addActionListener(statusEvent);

    updateUI(statusEvent.result);
    updateUI2(statusEvent.result);

    for (int i = 0; i < 1; i++) { //updateUI2 초기화
      updateUI2(statusRepository.select(member.getMilktId(), sub[0], desc[0], 1));
    }

    JScrollPane sp = new JScrollPane(p1);
    sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      @Override
      public void configureScrollBarColors() {
        thumbColor = color6;
        trackColor = Color.white;
      }
    });
    JScrollPane sp2 = new JScrollPane(p2);
    sp2.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      @Override
      public void configureScrollBarColors() {
        thumbColor = color6;
        trackColor = Color.white;
      }
    });

    pane.add(pa, BorderLayout.NORTH);
    pane.add(sp, BorderLayout.CENTER);

    pane2.add(pc, BorderLayout.NORTH);
    pane2.add(sp2, BorderLayout.CENTER);

    tab.addTab("       수강중       ", pane);
    tab.addTab("      수강 완료     ", pane2);

    cp.add(tab);

    pa.setBackground(color);
    p1.setBackground(color);
    p2.setBackground(color2);

    setUi();
  }

  public void updateUI(List<Study> studies) {
    p1.removeAll();

    pa.removeAll();
    pa.add(name);
    pa.add(box1);
    pa.add(box2);

    for (Study s : studies) {
      pb = new JPanel(new GridLayout(4, 2)); //수강 한 묶음
      label2 = new JLabel(s.getTeacher() + " 선생님");
      label1 = new JLabel(s.getGrade() + " " + s.getCname());
      label3 = new JLabel("총 " + s.getCcnt() + "강 중 " + s.getMcnt() + "강 달성(" + s.getProgress() + "%)");
      label4 = new JLabel(" ");
      label5 = new JLabel("학습 시작일 " + s.getSdate());
      label6 = new JLabel("마지막 학습일 " + s.getEdate());
      label7 = new JLabel(" ");
      btn = new JButton(new AbstractAction("수강 연장") {
        @Override
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(null, "수강을 연장하였습니다.");
        }
      });

      label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      label3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      label5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      label2.setForeground(Color.gray);
      label5.setForeground(color4);
      label6.setForeground(color5);
      btn.setBounds(10, 10, 10, 10);

      pb.add(label1);
      pb.add(label2);
      pb.add(label3);
      pb.add(label4);
      pb.add(label5);
      pb.add(label6);
      pb.add(label7);
      pb.add(btn);
      p1.add(pb);

      pb.setBackground(color);
      btn.setBackground(color3);
      btn.setBorder(BorderFactory.createLineBorder(color3));
    }
    p1.revalidate();
    p1.repaint();
  }

  public void updateUI2(List<Study> studies) {
    p2.removeAll();

    pc.removeAll();
    pc.add(name2);
    pc.add(box3);
    pc.add(box4);

    for (Study s : studies) {
      pd = new JPanel(new GridLayout(4, 2)); //수강 한 묶음
      label9 = new JLabel(s.getTeacher() + " 선생님");
      label8 = new JLabel(s.getGrade() + " " + s.getCname());
      label10 = new JLabel("총 " + s.getCcnt() + "강 중 " + s.getMcnt() + "강 달성(" + s.getProgress() + "%)");
      label11 = new JLabel(" ");
      label12 = new JLabel("학습 시작일 " + s.getSdate());
      label13 = new JLabel("마지막 학습일 " + s.getEdate());

      label8.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      label10.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      label12.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      pd.add(label8);
      pd.add(label9);
      pd.add(label10);
      pd.add(label11);
      pd.add(label12);
      pd.add(label13);
      p2.add(pd);

      label9.setForeground(Color.gray);
      label12.setForeground(color4);
      label13.setForeground(color5);
      pc.setBackground(color2);
      pd.setBackground(color2);
    }
    p2.repaint();
  }


  void setUi() {
    setVisible(true);
    setSize(400, 710);
    setLocation(200, 100);
    setResizable(false);
    setLocationRelativeTo(null);
  }
}