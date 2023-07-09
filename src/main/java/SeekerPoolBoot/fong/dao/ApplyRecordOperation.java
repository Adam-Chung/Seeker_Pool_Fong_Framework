package SeekerPoolBoot.fong.dao;

import java.util.List;

import SeekerPoolBoot.fong.vo.ApplyRecordShow;

public interface ApplyRecordOperation {
	
	public int findTotalCount(int memId, String keyWord, int filterNum);
	
	public List<ApplyRecordShow> findByPage(int memId, int start, int pageSize, String keyWord, int filterNum);
}
