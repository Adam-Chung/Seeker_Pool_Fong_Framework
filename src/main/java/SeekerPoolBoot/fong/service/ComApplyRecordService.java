package SeekerPoolBoot.fong.service;

import SeekerPoolBoot.fong.vo.CompanyMemberShow;
import SeekerPoolBoot.fong.vo.InterviewTimeShow;
import SeekerPoolBoot.fong.vo.Member;

public interface ComApplyRecordService {

	/**
	 * 獲取會員資訊
	 * @param memId
	 * @return
	 */
	Member getMemberById(Integer memId);

	/**
	 * 寄發郵件
	 * @param memId
	 * @param company
	 * @param jobNo
	 * @param contextPath
	 * @param interviewTime1
	 * @param interviewTime2
	 * @param interviewTime3
	 * @return
	 */
	boolean InterviewInvite(Integer memId, CompanyMemberShow company, Integer jobNo, String contextPath,
			InterviewTimeShow interviewTime1, InterviewTimeShow interviewTime2, InterviewTimeShow interviewTime3);


}
