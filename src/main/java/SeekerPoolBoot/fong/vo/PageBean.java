package SeekerPoolBoot.fong.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter  
@Setter
@ToString 
public class PageBean<T> implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
    private int totalCount; //總紀錄數量
    private int totalPage; //總頁數
    private int currentPage; //當前頁碼
    private int pageSize; //每頁顯示條數
    private List<T> list; //每一頁顯示的資料(數據)集合

}
