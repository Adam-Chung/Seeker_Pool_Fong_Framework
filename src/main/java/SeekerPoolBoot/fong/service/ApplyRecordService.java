package SeekerPoolBoot.fong.service;

import SeekerPoolBoot.fong.vo.ApplyRecordShow;
import SeekerPoolBoot.fong.vo.PageBean;

public interface ApplyRecordService {

	/**
	 * 透過頁數獲取資料
	 * @param memId
	 * @param currentPage
	 * @param pageSize
	 * @param filterNum 
	 * @param keyWord 
	 * @return
	 */
	public PageBean<ApplyRecordShow> pageQuery(Integer memId, int currentPage, int pageSize, String keyWord, int filterNum);

	/**
	 * 取消面試
	 * @param comName
	 * @param jobName
	 * @param memId
	 * @param memName
	 */
	public void cancelInterview(String comName, String jobName, Integer memId, String memName);

	/**
	 * 更新面試時間
	 * @param jobId
	 * @param memId
	 * @param dateTime
	 */
	public void updateInterviewTime(Integer jobId, Integer memId, String dateTime);

}
