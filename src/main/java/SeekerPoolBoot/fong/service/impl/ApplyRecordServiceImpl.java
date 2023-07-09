package SeekerPoolBoot.fong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeekerPoolBoot.fong.dao.ApplyRecordRepository;
import SeekerPoolBoot.fong.dao.CompanyRepository;
import SeekerPoolBoot.fong.service.ApplyRecordService;
import SeekerPoolBoot.fong.util.SendEmailTextUtil;
import SeekerPoolBoot.fong.vo.ApplyRecordShow;
import SeekerPoolBoot.fong.vo.PageBean;

@Service
public class ApplyRecordServiceImpl implements ApplyRecordService {

	@Autowired
	private ApplyRecordRepository applyRecordRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public PageBean<ApplyRecordShow> pageQuery(Integer memId, int currentPage, int pageSize, String keyWord,
			int filterNum) {
		// 封裝pageBean
		PageBean<ApplyRecordShow> pb = new PageBean<ApplyRecordShow>();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);

		// 設置總記錄數
		int totalCount = applyRecordRepository.findTotalCount(memId, keyWord, filterNum);
		pb.setTotalCount(totalCount);

		// 設置當前頁數據集合
		int start = (currentPage - 1) * pageSize;// 開始的紀錄數
		List<ApplyRecordShow> list = applyRecordRepository.findByPage(memId, start, pageSize, keyWord, filterNum);

		pb.setList(list);

		// 設置總頁數
		int totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
		pb.setTotalPage(totalPage);

		System.out.println(pb);

		return pb;
	}

	@Override
	public void cancelInterview(String comName, String jobName, Integer memId, String memName) {
		
		applyRecordRepository.cancelInterview(memId, jobName, comName);

		// 發信通知寄企業
		String comEmail = companyRepository.getComEmailByComName(comName);
		
		System.out.println("comEmail  = " + comEmail);

		String emailTo = comEmail;
		String emailSubject = "【Seeker's Pool】 應徵者取消面試通知";
		String name = comName;
		String messageText = name + " 您好 <br> 應徵者: " + memName + " ，應徵職缺為 " + jobName
				+ " <br> 取消面試，再請到 Seeker's Pool 網站確認，謝謝";

		// 寄出郵件
		SendEmailTextUtil.sendMail(emailTo, emailSubject, messageText);
		
	}

	@Override
	public void updateInterviewTime(Integer jobId, Integer memId, String dateTime) {
		applyRecordRepository.updateInterviewTime(jobId, memId, dateTime);
	}

}
