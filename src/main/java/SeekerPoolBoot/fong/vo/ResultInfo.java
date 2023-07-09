package SeekerPoolBoot.fong.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter  
@Setter
@ToString 
public class ResultInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean flag;//後端返回結果正常為true，發生異常返回false
    private Object data;//返回结果 對象
    private String msg;//異常消息
}
