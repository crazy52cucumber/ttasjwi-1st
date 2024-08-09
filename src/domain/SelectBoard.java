package domain;

import java.util.Date;

public class SelectBoard {
  private int boardNo;
  private String title;
  private String article;
  private Date boardDate;
  private int boardYn;
  private int tspoonNo;

  public SelectBoard(int boardNo, String title, String article, Date boardDate, int boardYn, int tspoonNo) {
    this.boardNo = boardNo;
    this.title = title;
    this.article = article;
    this.boardDate = boardDate;
    this.boardYn = boardYn;
    this.tspoonNo = tspoonNo;
  }

  public int getBoardNo() {
    return boardNo;
  }

  public String getTitle() {
    return title;
  }

  public String getArticle() {
    return article;
  }

  public Date getBoardDate() {
    return boardDate;
  }

  public int getBoardYn() {
    return boardYn;
  }

  public int getTspoonNo() {
    return tspoonNo;
  }
}
