package SeekerPoolBoot.fong.service;

import SeekerPoolBoot.fong.vo.Article;
import SeekerPoolBoot.fong.vo.PageBean;

public interface ArticleService {

	/**
	 * 透過頁數獲取資料
	 * @param memId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean<Article> pageQuery(Integer memId, int currentPage, int pageSize);

	/**
	 * 刪除收藏文章
	 * @param arNoInteger
	 * @param memId
	 */
	void deletColArtByMemIdAndArNo(Integer arNoInteger, Integer memId);

}
