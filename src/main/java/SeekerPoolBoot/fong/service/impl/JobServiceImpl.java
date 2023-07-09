package SeekerPoolBoot.fong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeekerPoolBoot.fong.dao.CollectJobRepository;
import SeekerPoolBoot.fong.dao.JobRepository;
import SeekerPoolBoot.fong.service.JobService;
import SeekerPoolBoot.fong.vo.Job;
import SeekerPoolBoot.fong.vo.PageBean;

@Service
public class JobServiceImpl implements JobService{
	
	@Autowired
	private CollectJobRepository collectJobRepository;
	@Autowired
	private JobRepository jobRepository;

	@Override
	public PageBean<Job> pageQuery(Integer memId, int currentPage, int pageSize) {
		//封裝pageBean
        PageBean<Job> pb = new PageBean<Job>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        //設置總記錄數
        int totalCount = collectJobRepository.countByMemId(memId);
        pb.setTotalCount(totalCount);

        //設置當前頁數據集合
        int start = (currentPage - 1) * pageSize;//開始的紀錄數
        List<Job> list = jobRepository.findByMemId(memId, start, pageSize);
        pb.setList(list);

        //設置總頁數
        int totalPage = (totalCount % pageSize)== 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
	}

	@Override
	public void deleteByJobNoAndMemId(Integer jobNoInteger, Integer memId) {
		collectJobRepository.deleteByJobNoAndMemId(jobNoInteger, memId);;
		
	}

}
