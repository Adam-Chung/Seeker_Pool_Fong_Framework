package SeekerPoolBoot.fong.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import SeekerPoolBoot.fong.service.ComApplyRecordService;
import SeekerPoolBoot.fong.util.RandCodeUtil;
import SeekerPoolBoot.fong.vo.CompanyMemberShow;
import SeekerPoolBoot.fong.vo.InterviewTimeShow;
import SeekerPoolBoot.fong.vo.Member;
import SeekerPoolBoot.fong.vo.ResultInfo;

@RestController 
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
    private RedisTemplate<String,String> redis;
	
	@Autowired
	private ComApplyRecordService comApplyRecordService;
	
	
	
	@PostMapping("/interviewInvite")
	public ResultInfo interviewInvite() {
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String date3 = request.getParameter("date3");

		Integer memId = Integer.valueOf(request.getParameter("memId"));
		Integer jobNo = Integer.valueOf(request.getParameter("jobNo"));

		Member member = comApplyRecordService.getMemberById(memId);

		// ======目前先用假登入資料==========
//		Integer comId = (Integer) req.getSession().getAttribute("companyLogin");
		CompanyMemberShow company = new CompanyMemberShow();
		company.setComMemId(1);
		company.setComName("好微笑公司");

		// 獲取專案名稱
		String contextPath = request.getContextPath();

		// 獲得InterviewTimeVo
		InterviewTimeShow interviewTime1 = new InterviewTimeShow();
		InterviewTimeShow interviewTime2 = new InterviewTimeShow();
		InterviewTimeShow interviewTime3 = new InterviewTimeShow();

		List<String> CheckTimeKeys = new ArrayList<String>(); // 此list建立為了將存入Redis的此次全部資料刪除

		for (int i = 0; i < 3; i++) {
			String randomCode = RandCodeUtil.getRandomCode(4);
			CheckTimeKeys.add(memId + randomCode);
		}

		// 填入第1個預約時間資訊
		interviewTime1.setCheckTimeKey(CheckTimeKeys.get(0));
		interviewTime1.setDateTime(date1);
		interviewTime1.setCompany(company);
		interviewTime1.setJobId(jobNo);
		interviewTime1.setMember(member);
		interviewTime1.setCheckTimeKeys(CheckTimeKeys);
		String json1 = new Gson().toJson(interviewTime1);
		redis.opsForValue().set(interviewTime1.getCheckTimeKey(), json1);
//		redis.opsForValue().set(interviewTime1.getCheckTimeKey(), json1, 20, TimeUnit.SECONDS);  //時效性

		// 填入第2個預約時間資訊
		if (date2 != null) {
			interviewTime2.setCheckTimeKey(CheckTimeKeys.get(1));
			interviewTime2.setDateTime(date2);
			interviewTime2.setCompany(company);
			interviewTime2.setJobId(jobNo);
			interviewTime2.setMember(member);
			interviewTime2.setCheckTimeKeys(CheckTimeKeys);
			String json2 = new Gson().toJson(interviewTime2);
			redis.opsForValue().set(interviewTime2.getCheckTimeKey(), json2);

		}
		// 填入第3個預約時間資訊
		if (date3 != null) {
			interviewTime3.setCheckTimeKey(CheckTimeKeys.get(2));
			interviewTime3.setDateTime(date3);
			interviewTime3.setCompany(company);
			interviewTime3.setJobId(jobNo);
			interviewTime3.setMember(member);
			interviewTime3.setCheckTimeKeys(CheckTimeKeys);
			String json3 = new Gson().toJson(interviewTime3);
			redis.opsForValue().set(interviewTime3.getCheckTimeKey(), json3);
		}

		boolean flag = comApplyRecordService.InterviewInvite(memId, company, jobNo, contextPath, interviewTime1, interviewTime2, interviewTime3);
		
		ResultInfo info = new ResultInfo(); // 用於封裝資訊回傳前端
		// 響應結果
		if (flag) {
			// 寄信成功
			info.setFlag(true);
		} else {
			// 寄信失敗
			info.setFlag(false);
		}
		redis.getConnectionFactory().getConnection().close();
		return info;
	}
}
