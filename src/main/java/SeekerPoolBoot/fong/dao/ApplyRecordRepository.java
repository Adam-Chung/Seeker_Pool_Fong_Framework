package SeekerPoolBoot.fong.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import SeekerPoolBoot.fong.vo.ApplyRecordShow;

public interface ApplyRecordRepository extends JpaRepository<ApplyRecordShow, Integer>, ApplyRecordOperation {

	@Transactional
	@Modifying
	@Query(value = "UPDATE apply_record a SET a.hire_status = 4,  a.inter_date = null WHERE mem_id = :memId and job_no = (select distinct job_no from job where job_name = :jobName ) and com_mem_id = (select distinct COM_MEM_ID from company_member where com_name = :comName );", nativeQuery = true)
	void cancelInterview(Integer memId, String jobName, String comName);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO apply_record (mem_id, job_no, COM_MEM_ID, hire_status) VALUES ( :memId , :jobNo , :comMemId ,3);", nativeQuery = true)
	void addInterviewInvite(Integer memId, Integer jobNo, Integer comMemId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE apply_record  SET hire_status = 1, inter_date = :dateTime WHERE mem_id = :memId and job_no = :jobId ;", nativeQuery = true)
	void updateInterviewTime(Integer jobId, Integer memId, String dateTime);

}
