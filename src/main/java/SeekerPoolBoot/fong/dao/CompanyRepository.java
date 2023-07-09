package SeekerPoolBoot.fong.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SeekerPoolBoot.fong.vo.CompanyMemberShow;

public interface CompanyRepository extends JpaRepository<CompanyMemberShow, Integer> {


	@Query(value = "select b.com_mem_id, com_name from block_company b, company_member c where mem_id = :memId and b.com_mem_id = c.com_mem_id;", nativeQuery = true)
	List<CompanyMemberShow> findBlockComsByMemId(Integer memId);


	@Query(value = "select DISTINCT COM_MEM_ID from company_member  where com_name = :comName ;", nativeQuery = true)
	Integer getComIdByName(String comName);
	

	@Query(value = "select com_email from company_member where com_name = :comName ;", nativeQuery = true)
	String getComEmailByComName(String comName); 
	
}
