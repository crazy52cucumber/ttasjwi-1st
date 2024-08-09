package domain.dto;

public class SelectMilktMember {
  private String tspoonMemberName;
  private String milktMemberName;
  private String milktid;

  public SelectMilktMember(String tspoonMemberName, String milktMemberName, String milktid) {
    this.tspoonMemberName = tspoonMemberName;
    this.milktMemberName = milktMemberName;
    this.milktid = milktid;
  }

  public String getTspoonMemberName() {
    return tspoonMemberName;
  }

  public String getMilktMemberName() {
    return milktMemberName;
  }

  public String getMilktid() {
    return milktid;
  }
}
