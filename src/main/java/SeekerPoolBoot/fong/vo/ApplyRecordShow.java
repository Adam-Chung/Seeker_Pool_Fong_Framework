package SeekerPoolBoot.fong.vo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Getter  
@Setter
@ToString 
public class ApplyRecordShow implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String comName;
	private String jobName;
	private Date applyDate;
	private String interDate;
	private Integer hireStatus;
	
}
