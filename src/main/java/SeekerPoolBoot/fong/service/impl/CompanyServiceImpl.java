package SeekerPoolBoot.fong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeekerPoolBoot.fong.dao.BlockCompanyRepository;
import SeekerPoolBoot.fong.dao.CompanyRepository;
import SeekerPoolBoot.fong.service.CompanyService;
import SeekerPoolBoot.fong.vo.BlockCompany;
import SeekerPoolBoot.fong.vo.CompanyMemberShow;
import SeekerPoolBoot.fong.vo.ResultInfo;


@Service
public class CompanyServiceImpl implements CompanyService{
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private BlockCompanyRepository blockCompanyRepository;

	@Override
	public List<CompanyMemberShow> findBlockComsByMemId(Integer memId) {
		return companyRepository.findBlockComsByMemId(memId);
	}

	
	@Override
	public void deletBlockComByName(Integer memId, String deleteBlockCom) {
		Integer comMemId = companyRepository.getComIdByName(deleteBlockCom);
		System.out.println("comMemId = " + comMemId);
		
		blockCompanyRepository.deleteByMemIdAndComMemId(memId, comMemId);
	}

	@Override
	public ResultInfo addBlockComByName(Integer memId, String addCompanyName) {
		ResultInfo info = new ResultInfo(); 
		//先確認是否在資料庫有其公司
		Integer comMemId = companyRepository.getComIdByName(addCompanyName);
		
		if(comMemId == null) {
			//新增失敗，找不到該企業
			info.setFlag(false);
			info.setMsg("找不到該企業");
		}else {
			//確認是否已存在屏蔽名單中
			Boolean flag =  blockCompanyRepository.existsByMemIdAndComMemId(memId, comMemId);
			if(flag == true){
				//新增失敗，已存在於屏蔽名單
				info.setFlag(false);
				info.setMsg("該企業已存在於您的屏蔽名單中");
			}else {
				//新增成功
				BlockCompany bc = new BlockCompany();
				bc.setComMemId(comMemId);
				bc.setMemId(memId);
				
				blockCompanyRepository.save(bc);
				info.setFlag(true);
				info.setData(addCompanyName);
			}
		}
		return info;
	}


}
