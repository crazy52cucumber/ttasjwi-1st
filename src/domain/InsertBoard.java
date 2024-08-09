package domain;

import java.util.Date;

public class InsertBoard {
  private String title;
  private String article;
  private Date boardDate;
  private int boardYn;
  private int tspoonNo;

  public InsertBoard(String title, String article, int boardYn, int tspoonNo) {
    this.title = title;
    this.article = article;
    this.boardYn = boardYn;
    this.tspoonNo = tspoonNo;
  }

  public String getTitle() {
    return title;
  }

  public String getArticle() {
    return article;
  }

  public int getTspoonNo() {
    return tspoonNo;
  }
}
