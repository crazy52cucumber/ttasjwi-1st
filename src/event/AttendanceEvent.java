package event;
import domain.SelectMember;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import repository.AttendanceRepository;
import ui.MainFormUI; 

public class AttendanceEvent {
    private SelectMember member;
    private AttendanceRepository AR;
    private MainFormUI mainForm;
    private Date childCheck;
    private Date currentdaDate = new Date();
    
    public AttendanceEvent(AttendanceRepository AR, SelectMember member, MainFormUI mainForm){
        this.mainForm = mainForm;
        this.member = member;
        this.AR = AR;
    
       
    }
    

    public void attendanceshow(){ //메인폼이랑 어케 연결함
        
        childCheck = AR.getAttendanceDate();
        JLabel attendancLabel = mainForm.getMyLabel();
        ImageIcon yesKidsIcon1 = new ImageIcon("images/icon/complete110.png");
        ImageIcon yesKidsIcon2 = new ImageIcon("images/icon/rest110.png");
    
        if(member.getMilktId() != null){//자녀가 있을 경우
            if(childCheck == currentdaDate){
                attendancLabel.setIcon(yesKidsIcon1);
            }else if(childCheck != currentdaDate){
                attendancLabel.setIcon(yesKidsIcon2);
            }
        }else{ //자녀가 없으면
            System.out.println("자녀가 등록되지 않았습니다");
        }      
    }


}
