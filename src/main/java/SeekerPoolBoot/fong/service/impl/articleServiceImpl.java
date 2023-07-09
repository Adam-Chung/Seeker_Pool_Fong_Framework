package SeekerPoolBoot.fong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeekerPoolBoot.fong.dao.ArticleRepository;
import SeekerPoolBoot.fong.dao.CollectRepository;
import SeekerPoolBoot.fong.service.ArticleService;
import SeekerPoolBoot.fong.vo.Article;
import SeekerPoolBoot.fong.vo.PageBean;

@Service
public class articleServiceImpl implements ArticleService{
	
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public PageBean<Article> pageQuery(Integer memId, int currentPage, int pageSize) {
		//封裝pageBean
        PageBean<Article> pb = new PageBean<Article>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        //設置總記錄數
        int totalCount = collectRepository.countByMemId(memId);
        pb.setTotalCount(totalCount);

        //設置當前頁數據集合
        int start = (currentPage - 1) * pageSize;//開始的紀錄數
        List<Article> list = articleRepository.findByMemId(memId, start, pageSize);
        pb.setList(list);

        //設置總頁數
        int totalPage = (totalCount % pageSize)== 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
	}

	@Override
	public void deletColArtByMemIdAndArNo(Integer arNoInteger, Integer memId) {
		collectRepository.deleteByArNoAndMemId(arNoInteger, memId);;
	}
}
