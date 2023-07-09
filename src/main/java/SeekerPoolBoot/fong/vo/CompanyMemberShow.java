package SeekerPoolBoot.fong.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter  
@Setter
@ToString 
public class CompanyMemberShow implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	private Integer comMemId;
	private String comName;	
}
