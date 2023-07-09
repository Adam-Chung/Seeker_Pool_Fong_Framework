package SeekerPoolBoot.fong.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity  
@Getter  
@Setter
@ToString 
public class Member implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer memId;
	private String memAddress; //地區
	private String memName;
	private String memGender;
	private String memPic;
	private String memEmail;
	private String memMobile;
	private String memCollege;
	private String memDepartment;
	private String memLang;
	private String memBio;
	private String skNo;
	private String memAccount;
	private String memPassword;
	private Integer nlSub;
	private Integer memStatus;
	private Integer cvStatus;
	
	
}
