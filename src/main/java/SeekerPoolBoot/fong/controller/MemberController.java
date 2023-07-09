package SeekerPoolBoot.fong.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import SeekerPoolBoot.fong.service.ApplyRecordService;
import SeekerPoolBoot.fong.service.ArticleService;
import SeekerPoolBoot.fong.service.CompanyService;
import SeekerPoolBoot.fong.service.JobService;
import SeekerPoolBoot.fong.service.MemberService;
import SeekerPoolBoot.fong.vo.ApplyRecordShow;
import SeekerPoolBoot.fong.vo.Article;
import SeekerPoolBoot.fong.vo.CompanyMemberShow;
import SeekerPoolBoot.fong.vo.InterviewTimeShow;
import SeekerPoolBoot.fong.vo.Job;
import SeekerPoolBoot.fong.vo.Member;
import SeekerPoolBoot.fong.vo.PageBean;
import SeekerPoolBoot.fong.vo.ResultInfo;

@RestController 
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisTemplate<String, String> redis; 

	@Autowired
	private ArticleService articleService;

	@Autowired
	private JobService jobService;

	@Autowired
	private ApplyRecordService applyRecordService;

	/**
	 * 註冊
	 */
	@PostMapping
	public ResultInfo registerMem(@RequestParam Map<String, Object> params, @RequestParam("memPic") Part part) throws IOException {

		Member member = new Member();
		try {
			BeanUtils.populate(member, params); // 表單name要跟成員名稱一樣
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 獲取照片
		Part filepart = part;
		String filename = filepart.getSubmittedFileName();

		String saveDirectory = "/images_uploaded"; // 上傳檔案的目的地目錄;
		String realPath = request.getServletContext().getRealPath(saveDirectory);
		File fsaveDirectory = new File(realPath);
		if (!fsaveDirectory.exists()) {
			fsaveDirectory.mkdirs();
		} 

		File f = new File(fsaveDirectory, filename); // 真實路徑(連動端，實際運作取用)
		filepart.write(f.toString()); // part類別的方法write() 利用File物件, 寫入指定目地目錄上傳


		// 將路徑存入物件中
		member.setMemPic(request.getContextPath() + saveDirectory + "/" + filename);
		member.setMemAddress(request.getParameter("city") + request.getParameter("district"));

		ResultInfo info = new ResultInfo();
		Boolean flag = memberService.registerMember(member);

		if (flag) {
			// 註冊成功
			info.setFlag(true);
			// 註冊成功，也相當於登入，後續用token完成登入儲存
			Member memberLogin = memberService.loginMember(member);
			Integer memId = memberLogin.getMemId();
			request.getSession().setAttribute("memberLogin", memId);

		} else {
			// 註冊失敗
			info.setFlag(false);
			info.setMsg("註冊失敗，帳號重複");
		}
		return info;
	}
	
	
	/**
	 * 會員登入2 接收前端轉換過的JSON表單
	 */
	@PostMapping("/login")
	public ResultInfo memberLogin(@RequestBody Member member) {

		Member memberLogin = memberService.loginMember(member);
		ResultInfo info = new ResultInfo(); // 用於封裝資訊回傳前端
		if (memberLogin != null) {
			// 登入成功
			info.setFlag(true);
			Integer memId = memberLogin.getMemId();
			request.getSession().setAttribute("memberLogin", memId);
		} else {
			// 登入失敗
			info.setFlag(false);
			info.setMsg("帳號或密碼錯誤");
		}
		return info;
	}

	/**
	 * 得到屏蔽企業
	 */
	@GetMapping("/getBlockCom")
	public List<CompanyMemberShow> getBlockCom() {
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		return companyService.findBlockComsByMemId(memId);
	}

	/**
	 * 刪除屏蔽企業
	 */
	@PostMapping("/delBlockCom")
	public void delBlockCom(@RequestParam String deleteBlockCom) {
		deleteBlockCom = deleteBlockCom.trim();
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		companyService.deletBlockComByName(memId, deleteBlockCom);
	}

	/**
	 * 新增屏蔽企業
	 */
	@PostMapping("/addBlockCom")
	public ResultInfo addBlockCom(@RequestParam String addCompanyName) {
		addCompanyName = addCompanyName.trim();
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		ResultInfo info = companyService.addBlockComByName(memId, addCompanyName);
		return info;
	}

	/**
	 * 秀出會員資料
	 */
	@GetMapping("/showInfo")
	public Member showInfo() {
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		return memberService.getMemberByMemyId(memId);
	}

	/**
	 * 修改會員資料
	 */
	@PostMapping("/updateInfo")
	public void updateInfo(@RequestParam Map<String, Object> params) throws IOException {
//		抓圖片input並儲存於阿飄路徑
		Part filepart = null;
		String filename = null;
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		Member orgMember = memberService.getMemberByMemyId(memId);

		try {
			filepart = request.getPart("memPic"); 
		} catch (Exception e) {
//			e.printStackTrace(); 
		}

		if (filepart != null) { // 有照片更新
			filename = filepart.getSubmittedFileName();

			String saveDirectory = "/images_uploaded"; // 上傳檔案的目的地目錄; (不要在註解@處指定目的地路徑)
			String realPath = request.getServletContext().getRealPath(saveDirectory);
			File fsaveDirectory = new File(realPath);
			if (!fsaveDirectory.exists()) {
				fsaveDirectory.mkdirs();
			}

			File f = new File(fsaveDirectory, filename); // 建立一個檔案，用真實路徑
			filepart.write(f.toString()); 

			// 將路徑存入物件中
			orgMember.setMemPic(request.getContextPath() + saveDirectory + "/" + filename);
			memberService.updateMember(orgMember);

		} else { // 沒照片更新
			Member member = new Member();
			Map<String, String[]> map = request.getParameterMap();
			try {
				BeanUtils.populate(member, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			member.setMemPic(orgMember.getMemPic());
			member.setMemId(orgMember.getMemId());
			member.setMemStatus(orgMember.getMemStatus());
			memberService.updateMember(member);

		}
	}

	/*
	 * 在header秀出會員名稱
	 */
	@GetMapping("/headerShowName")
	public ResultInfo headerShowName() { 
		if (request.getParameter("logout") != null) { // 要登出
			request.getSession().removeAttribute("memberLogin");
		} else {
			Object memId = request.getSession().getAttribute("memberLogin");

			ResultInfo info = new ResultInfo(); // 用於封裝資訊回傳前端
			if (memId != null) {
				// 有登入
				Integer id = (Integer) memId;
				Member member = memberService.getMemberByMemyId(id);

				info.setFlag(true);
				info.setData(member);
			} else {
				// 沒登入
				info.setFlag(false);
			}
			return info;
		}
		return null;
	}
	

	/*
	 * 寄驗證信
	 */
	@PostMapping("/sendMail")
	public void sendMail() {

		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		Member member = memberService.getMemberByMemyId(memId);
		// 命名Redis中的key
		String key = member.getMemAccount() + "-checkCode";

		// 寄信前先刪除之前的驗證碼
		redis.delete(key);

		String contextPath = request.getContextPath(); // 獲取專案名稱
		String checkCode = memberService.sendCheckCode(member, contextPath);

		// 儲存信箱驗證碼到Redis中，定時20秒銷毀
		redis.opsForValue().set(key, checkCode, 20, TimeUnit.SECONDS);

		System.out.println(key + "郵件驗證--" + redis.opsForValue().get(key));
		redis.getConnectionFactory().getConnection().close();

	}
	
	/**
	 * 確認面試時間，從網址確認
	 */
	@PostMapping("/checkCode")
	public ResultInfo checkCode(@RequestParam("checkCode") String inputCheckCode) {
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		Member member = memberService.getMemberByMemyId(memId);

		// 命名Redis中的key
		String key = member.getMemAccount() + "-checkCode";
		ResultInfo info = new ResultInfo(); // 用於封裝資訊回傳前端

		String checkCode = redis.opsForValue().get(key);
		if (inputCheckCode.trim().equals(checkCode)) {
			// 驗證碼正確
			info.setFlag(true);
			member.setMemStatus(1);
			memberService.updateMember(member);

			redis.delete(key);

		} else {
			// 驗證錯誤
			info.setFlag(false);
			info.setMsg("驗證碼錯誤或過時，請確認");
		}
		redis.getConnectionFactory().getConnection().close();
		return info;
	}

	/**
	 * 顯示收藏文章
	 */
	@GetMapping("/collectArticle")
	public PageBean<Article> collectArticle() {
		// 接收參數
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");

		// 處理參數
		int currentPage = 1;// 當前頁碼 默認
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		int pageSize = 5; // 每頁顯示條數 默認
		if (pageSizeStr != null && pageSizeStr.length() > 0) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		// 調用service查詢PageBean對象
		PageBean<Article> pb = articleService.pageQuery(memId, currentPage, pageSize);

		// 轉為json寫回客戶端
		return pb;
	}

	/**
	 * 刪除收藏文章
	 */
	@PostMapping("/deletCollectArticle")
	public void deletCollectArticle(@RequestParam String arNo) {
		Integer arNoInteger = Integer.valueOf(arNo.trim());
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");

		articleService.deletColArtByMemIdAndArNo(arNoInteger, memId);
	}

	/**
	 * 顯示收藏職缺
	 */
	@GetMapping("/collectJob")
	public PageBean<Job> collectJob() {
		// 接收參數
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");

		// 處理參數
		int currentPage = 1;// 當前頁碼 默認
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		int pageSize = 5; // 每頁顯示條數 默認
		if (pageSizeStr != null && pageSizeStr.length() > 0) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		// 調用service查詢PageBean對象
		PageBean<Job> pb = jobService.pageQuery(memId, currentPage, pageSize);

		// 轉為json寫回客戶端
		return pb;
	}

	/**
	 * 刪除收藏職缺
	 */
	@PostMapping("/deletCollectJob")
	public void deletCollectJob(@RequestParam String jobNo) {
		Integer jobNoInteger = Integer.valueOf(jobNo.trim());
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		jobService.deleteByJobNoAndMemId(jobNoInteger, memId);
	}

	/**
	 * 顯示應徵紀錄
	 */
	@GetMapping("/applyRecord")
	public PageBean<ApplyRecordShow> applyRecord() {
		// 接收參數
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		String keyWordStr = request.getParameter("keyWord");
		String filterNumStr = request.getParameter("filterNum");
		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");

		// 處理參數
		int currentPage = 1;// 當前頁碼 默認
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		int pageSize = 5; // 每頁顯示條數 默認
		if (pageSizeStr != null && pageSizeStr.length() > 0) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		String keyWord = ""; // 關鍵字
		if (keyWordStr != null && keyWordStr.length() > 0 && !("null".equals(keyWordStr))) {
			keyWord = keyWordStr.trim();
		}
		int filterNum = 100; // 狀態篩選器 默認100全部顯示
		if (filterNumStr != null && filterNumStr.length() > 0) {
			filterNum = Integer.parseInt(filterNumStr);
		}

		// 調用service查詢PageBean對象
		PageBean<ApplyRecordShow> pb = applyRecordService.pageQuery(memId, currentPage, pageSize, keyWord, filterNum);

		// 轉為json寫回客戶端
		return pb;
	}

	/**
	 * 取消面試
	 */
	@PostMapping("/cancelInterview")
	public void cancelInterview(@RequestParam String comName, @RequestParam String jobName) {

		Integer memId = (Integer) request.getSession().getAttribute("memberLogin");
		Member member = memberService.getMemberByMemyId(memId);
		applyRecordService.cancelInterview(comName.trim(), jobName.trim(), memId, member.getMemName());
	}

	
	/**
	 * 確認面試時間
	 */
	@PostMapping("/interviewInvite")
	public ResultInfo interviewInvite() {
		String checkbtn = request.getParameter("checkbtn");
		String checktime = request.getParameter("checktime");

		String hget = redis.opsForValue().get(checktime);
		InterviewTimeShow interviewTime;

		ResultInfo info = new ResultInfo(); // 用於封裝資訊回傳前端

		if (checkbtn == null) { // 沒按確認，只有轉跳到此頁
			if (hget == null) {
				// 回傳錯誤訊息
				info.setFlag(false);
				info.setMsg("勿亂改網址，請重新點選時段，或面試時間已確認完畢");
			} else {
				interviewTime = new Gson().fromJson(hget, new TypeToken<InterviewTimeShow>() {
				}.getType());
				info.setFlag(true);
				info.setData(interviewTime.getDateTime());
			}

		} else { // 按下確認

			if (hget == null) {
				// 回傳錯誤訊息
				info.setFlag(false);
				info.setMsg("勿亂改網址，請重新點選時段，或面試時間已確認完畢");
			} else {
				interviewTime = new Gson().fromJson(hget, new TypeToken<InterviewTimeShow>() {
				}.getType());
				applyRecordService.updateInterviewTime(interviewTime.getJobId(), interviewTime.getMember().getMemId(),
						interviewTime.getDateTime());
				info.setFlag(true);
				info.setMsg("確認成功，請登入後至應徵紀錄查看");

				// 刪除Redis裡面東西
				List<String> CheckTimeKeys = interviewTime.getCheckTimeKeys();

				for (int i = 0; i < CheckTimeKeys.size(); i++) {
					redis.delete(CheckTimeKeys.get(i));
				}
			}
		}
		redis.getConnectionFactory().getConnection().close();
		return info;
	}
}
