package SeekerPoolBoot.fong.service;

import java.util.List;

import SeekerPoolBoot.fong.vo.CompanyMemberShow;
import SeekerPoolBoot.fong.vo.ResultInfo;

public interface CompanyService {

	/**
	 * 用memid找到他的屏蔽公司們
	 * @param memId
	 * @return 
	 */
	List<CompanyMemberShow> findBlockComsByMemId(Integer memId);

	/**
	 * 刪除屏蔽企業
	 * @param memId
	 * @param deleteBlockCom
	 */
	void deletBlockComByName(Integer memId, String deleteBlockCom);

	/**
	 * 增加屏蔽企業
	 * @param memId
	 * @param addCompanyName
	 * @return
	 */
	ResultInfo addBlockComByName(Integer memId, String addCompanyName);

}
