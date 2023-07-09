package SeekerPoolBoot.fong.dao;

import org.springframework.transaction.annotation.Transactional;
//import javax.transaction.Transactional;  //跟上面都沒差

import org.springframework.data.jpa.repository.JpaRepository;

import SeekerPoolBoot.fong.vo.CollectJob;

public interface CollectJobRepository extends JpaRepository<CollectJob, Integer>{

	int countByMemId(Integer memId);

	@Transactional
	void deleteByJobNoAndMemId(Integer jobNoInteger, Integer memId);

}
