package domain;

import java.util.Date;

public class InsertMember {
  private int tspoon_no;
  private String id;
  private String password;
  private String name;
  private String phone_num;
  private String address;
  private Date join_date;
  private int sex;
  private int role;

  public InsertMember(String id, String password, String name, String phone_num, String address, Date join_date, int sex) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.phone_num = phone_num;
    this.address = address;
    this.join_date = join_date;
    this.sex = sex;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone_num() {
    return phone_num;
  }

  public String getAddress() {
    return address;
  }

  public int getSex() {
    return sex;
  }
}
