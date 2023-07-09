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
public class Job implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer jobNo;
	private Integer comMemId;
	private Integer ptNo;
	private String cityName; 
	private String districtName; 
	private Integer joNo;
	private String jobName;
	private String jobContent;
	private Integer jobSalary;
	private Integer jobType;
	private String jobAddress;
	private String jobOther;
	private Integer jobStatus;
	private Boolean jobUpload;
	private Boolean jobTop;
	
}