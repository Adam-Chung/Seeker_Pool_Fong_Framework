package SeekerPoolBoot.fong.vo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter  
@Setter
@ToString 
@IdClass(CollectJob.CollectJobId.class)  
public class CollectJob implements java.io.Serializable{  
	private static final long serialVersionUID = 1L; 
	
	@Id  
	private Integer memId;
	@Id 
	private Integer jobNo;
	private Date collectDate;
	
	
	@Getter  
	@Setter
	@ToString 
	public static class CollectJobId implements java.io.Serializable {   //複合主健要有
        private static final long serialVersionUID = 1L;
        private Integer memId;
        private Integer jobNo;
    }
}
