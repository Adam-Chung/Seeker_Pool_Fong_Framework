package SeekerPoolBoot.fong.dao;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.transaction.annotation.Transactional;
import javax.transaction.Transactional;

import SeekerPoolBoot.fong.vo.Collect;

public interface CollectRepository extends JpaRepository<Collect, Integer>{

	int countByMemId(Integer memId);

	@Transactional
	void deleteByArNoAndMemId(Integer arNoInteger, Integer memId);

}
