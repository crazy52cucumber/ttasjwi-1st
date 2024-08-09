package ui;

import domain.SelectMember;
import event.BoardEvent;
import event.WriteEvent;
import repository.BoardRepository;
import style.ColorSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardUI extends JFrame {

  private BoardRepository boardRepository;
  private JPanel boardPanel;
  private JPanel boardPanelParent;
  private JTextField textSearch;
  private JTable table;
  private JScrollPane scrollPane;
  private DefaultHeaderUI defaultHeader;
  private String colNames[] = {"글번호", "제목", "글쓴이", "작성일"};
  private DefaultTableModel model;


  public BoardUI(MainFormUI mainFormUI, LoginFormUI loginForm, SelectMember member) {
    boardRepository = new BoardRepository();
    model = new DefaultTableModel(colNames, 0) {
      public boolean isCellEditable(int r, int c) {
        return (c == 5) ? true : false;
      }
    };

    boardPanel = new JPanel();

    boardPanelParent = new JPanel();
    boardPanelParent.add(boardPanel);
    boardPanel.setBounds(0, 65, 400, 500);
    boardPanel.setBackground(ColorSet.BACKGROUND);
    boardPanelParent.setLayout(null);
    setContentPane(boardPanelParent);
    boardPanelParent.setBackground(ColorSet.BACKGROUND);

    // 헤더 추가
    defaultHeader = new DefaultHeaderUI("login", this, mainFormUI, member);
    add(defaultHeader);

    textSearch = new JTextField();
    textSearch.setBounds(30, 52, 208, 27);
    textSearch.setText("내용 검색");
    boardPanel.add(textSearch);
    textSearch.setColumns(10);
    textSearch.addMouseListener(new BoardEvent(textSearch, table, model));


    JButton writeButton = new JButton("글쓰기");
    writeButton.setBounds(250, 52, 109, 27);
    writeButton.addMouseListener(new WriteEvent(member, writeButton));
    boardPanel.add(writeButton);


    scrollPane = new JScrollPane();

    table = new JTable(model);
    table.setToolTipText("클릭하면 상세 보기");
    scrollPane.setViewportView(table);
    scrollPane.setPreferredSize(new Dimension(400, 500));

    boardPanel.add(scrollPane);
    table.setSurrendersFocusOnKeystroke(true);
    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    boardRepository.selectAllArticle(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.getColumn("글번호").setPreferredWidth(20);
    table.setRowHeight(50);
    textSearch.addKeyListener(new BoardEvent(textSearch, table, model));
    table.addMouseListener(new BoardEvent(member));

    setVisible(true);
    setSize(400, 710);
    setResizable(false);
    setLocationRelativeTo(null);
  }

}
