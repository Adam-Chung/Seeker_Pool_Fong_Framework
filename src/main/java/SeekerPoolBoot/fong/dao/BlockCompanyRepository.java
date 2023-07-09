package SeekerPoolBoot.fong.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import SeekerPoolBoot.fong.vo.BlockCompany;

public interface BlockCompanyRepository extends JpaRepository<BlockCompany, Integer> {


	@Transactional
	void deleteByMemIdAndComMemId(Integer memId, Integer comMemId);

	Boolean existsByMemIdAndComMemId(Integer memId, Integer comMemId);

	
	
}
