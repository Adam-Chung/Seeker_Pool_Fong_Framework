package SeekerPoolBoot.fong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeekerPoolBoot.fong.dao.ApplyRecordRepository;
import SeekerPoolBoot.fong.dao.JobRepository;
import SeekerPoolBoot.fong.dao.MemberRepository;
import SeekerPoolBoot.fong.service.ComApplyRecordService;
import SeekerPoolBoot.fong.util.SendEmailTextUtil;
import SeekerPoolBoot.fong.vo.CompanyMemberShow;
import SeekerPoolBoot.fong.vo.InterviewTimeShow;
import SeekerPoolBoot.fong.vo.Member;

@Service
public class ComApplyRecordServiceImpl implements ComApplyRecordService {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ApplyRecordRepository applyRecordRepository;
	@Autowired
	private JobRepository jobRepository;

	@Override
	public Member getMemberById(Integer memId) {
		return memberRepository.getByMemId(memId);
	}

	@Override
	public boolean InterviewInvite(Integer memId, CompanyMemberShow company, Integer jobNo, String contextPath,
			InterviewTimeShow interviewTime1, InterviewTimeShow interviewTime2, InterviewTimeShow interviewTime3) {
		// 加入資料庫
		applyRecordRepository.addInterviewInvite(memId, jobNo, company.getComMemId());

		String emailTo;
		String emailSubject;
		String messageText;
		
		// 寄"有連結"的信給該人才
		emailTo = interviewTime1.getMember().getMemEmail();
		emailSubject = "【Seeker's Pool】 您有一則面試邀約";
		String name = interviewTime1.getMember().getMemName();
		String jobName = jobRepository.getJobNameByJobNo(jobNo);

		// ===========有表格的信件內容===========================
		messageText = name + " 您好， <br> 您有來自 " + company.getComName() + " 公司 " + jobName
				+ "的面試邀約，請確認您可以面試的一個時段，點擊該時段連結即可確認，請於3天內回覆，謝謝! <br> <table border='1'><tr><th></th> <th>可選時段</th></tr> <tr><td>時段一</td><td>  <a href=\'http://localhost:8080"
				+ contextPath + "/front-end/member/member/checkinterviewtime.html?checktime="
				+ interviewTime1.getCheckTimeKey() + "'>" + interviewTime1.getDateTime() + "</a>  </td></tr>";

		if (interviewTime2.getDateTime() != null) {
			messageText += "<tr><td>時段二</td><td> <a href=\'http://localhost:8080" + contextPath
					+ "/front-end/member/member/checkinterviewtime.html?checktime=" + interviewTime2.getCheckTimeKey()
					+ "'>" + interviewTime2.getDateTime() + "</a>  </td></tr>";
		}

		if (interviewTime3.getDateTime() != null) {
			messageText += "<tr><td>時段三</td><td> <a href=\'http://localhost:8080" + contextPath
					+ "/front-end/member/member/checkinterviewtime.html?checktime=" + interviewTime3.getCheckTimeKey()
					+ "'>" + interviewTime3.getDateTime() + "</a>  </td></tr>";
		}

		messageText += "</table>";


		// 寄出郵件
		boolean flag = SendEmailTextUtil.sendMail(emailTo, emailSubject, messageText);
		return flag;

	}

}
