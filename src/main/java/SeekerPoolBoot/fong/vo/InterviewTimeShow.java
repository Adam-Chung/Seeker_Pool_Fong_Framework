package SeekerPoolBoot.fong.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter  
@Setter
@ToString 
public class InterviewTimeShow implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String dateTime;
	private CompanyMemberShow company;
	private Integer jobId;
	private Member member;
	private String checkTimeKey;
	private List<String> CheckTimeKeys;
	
}