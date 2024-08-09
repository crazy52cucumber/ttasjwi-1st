package domain.dto;

public class UpdateMember {
  private String id;
  private String password;
  private String phoneNum;
  private String address;

  public UpdateMember(String id, String password, String phoneNum, String address) {
    this.id = id;
    this.password = password;
    this.phoneNum = phoneNum;
    this.address = address;
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public String getAddress() {
    return address;
  }
}
