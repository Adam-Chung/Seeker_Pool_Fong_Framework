package SeekerPoolBoot.fong.service;

import SeekerPoolBoot.fong.vo.Job;
import SeekerPoolBoot.fong.vo.PageBean;

public interface JobService {

	/**
	 * 透過頁數獲取資料
	 * @param memId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean<Job> pageQuery(Integer memId, int currentPage, int pageSize);

	
	/**
	 * 刪除收藏職缺
	 * @param jobNoInteger
	 * @param memId
	 */
	void deleteByJobNoAndMemId(Integer jobNoInteger, Integer memId);

}
