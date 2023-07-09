package SeekerPoolBoot.fong.service;

import SeekerPoolBoot.fong.vo.Member;

public interface MemberService {

	/**
	 * 註冊用戶
	 * @param member
	 * @return
	 */
	Boolean registerMember(Member member);


	/**
	 * 用戶登入
	 * @param member
	 * @return
	 */
	Member loginMember(Member member);


	/**
	 * 透過會員id獲得完整資訊
	 * @param memId
	 * @return
	 */
	Member getMemberByMemyId(Integer memId);


	/**
	 * 更新會員資訊
	 * @param orgMember
	 */
	void updateMember(Member member);


	/**
	 * 寄驗證信
	 * @param member
	 * @param contextPath
	 * @return
	 */
	String sendCheckCode(Member member, String contextPath);

	
}
