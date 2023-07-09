package SeekerPoolBoot.fong.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SeekerPoolBoot.fong.vo.Job;

public interface JobRepository extends JpaRepository<Job, Integer>{

	
	@Query(value = "select * from JOB, COLLECT_JOB c where JOB.JOB_NO = c.JOB_NO AND MEM_ID = :memId limit :start , :pageSize ;", nativeQuery = true)
	List<Job> findByMemId(Integer memId, int start, int pageSize);

	
	@Query(value = "select job_name from job where job_no = ?; ", nativeQuery = true)
	String getJobNameByJobNo(Integer jobNo);

}
