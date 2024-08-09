package domain;

import java.util.Date;

public class SelectMember {
  private int tspoonNo;
  private String id;
  private String name;
  private String phoneNum;
  private String address;
  private Date joinDate;
  private String milktId;
  private int sex;

  public SelectMember(int tspoonNo, String id, String name, String phoneNum, String address, Date joinDate, int sex) {
    this.tspoonNo = tspoonNo;
    this.id = id;
    this.name = name;
    this.phoneNum = phoneNum;
    this.address = address;
    this.joinDate = joinDate;
    this.sex = sex;
  }

  public int getTspoonNo() {
    return tspoonNo;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public String getAddress() {
    return address;
  }

  public Date getJoinDate() {
    return joinDate;
  }

  public int getSex() {
    return sex;
  }

  public void setMilktId(String milktId) {
    this.milktId = milktId;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMilktId() {
    return milktId;
  }

  public int getSex(int sex) {return sex;}

  public void setSex(int sex){
    this.sex =sex;
  }

  @Override
  public String toString() {
    return "name: " + name + ", milktId: " + milktId;
  }
}
