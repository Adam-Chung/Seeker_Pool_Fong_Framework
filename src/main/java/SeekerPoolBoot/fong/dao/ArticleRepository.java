package SeekerPoolBoot.fong.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SeekerPoolBoot.fong.vo.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer>{

	
	@Query(value = "select * from article,collect where article.AR_NO = collect.AR_NO and collect.MEM_ID = :memId  limit :start , :pageSize  ", nativeQuery = true)
	List<Article> findByMemId(Integer memId, int start, int pageSize);

}
