package domain;

import java.sql.Date;

public class Study {
  private String name;
  private String grade;
  private String teacher;
  private String subject;
  private String cname;
  private int mcnt;
  private int ccnt;
  private int progress;
  private Date sdate;
  private Date edate;

  public Study(String name, String grade, String teacher, String subject,
               String cname, int mcnt, int ccnt, int progress, Date sdate, Date edate) {
    super();
    this.name = name;
    this.grade = grade;
    this.teacher = teacher;
    this.subject = subject;
    this.cname = cname;
    this.mcnt = mcnt;
    this.ccnt = ccnt;
    this.progress = progress;
    this.sdate = sdate;
    this.edate = edate;
  }

  public String getName() {
    return name;
  }

  public String getGrade() {
    return grade;
  }

  public String getTeacher() {
    return teacher;
  }

  public String getSubject() {
    return subject;
  }

  public String getCname() {
    return cname;
  }

  public int getMcnt() {
    return mcnt;
  }

  public int getCcnt() {
    return ccnt;
  }

  public int getProgress() {
    return progress;
  }

  public Date getSdate() {
    return sdate;
  }

  public Date getEdate() {
    return edate;
  }
}