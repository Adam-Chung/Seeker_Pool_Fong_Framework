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
public class Article implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer arNo;
	private Integer memId;
	private Date arPubTime;
	private String arTitle;
	private String arContent;
	private String arImg;
	private Integer arStatus;
	private Integer arHits;
	

}
