package SeekerPoolBoot.fong.vo;

import java.sql.Date;

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
public class Collect {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer colArNo;
	private Integer memId;
	private Integer arNo;
	private Date colTime;
	
}
