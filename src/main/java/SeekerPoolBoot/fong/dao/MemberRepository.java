package SeekerPoolBoot.fong.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import SeekerPoolBoot.fong.vo.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Boolean existsByMemAccount(String memAccount);

	Member findByMemAccountAndMemPassword(String memAccount, String memPassword);

	Member getByMemId(Integer memId);
}
