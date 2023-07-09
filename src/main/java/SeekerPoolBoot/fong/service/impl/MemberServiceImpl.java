package SeekerPoolBoot.fong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeekerPoolBoot.fong.dao.MemberRepository;
import SeekerPoolBoot.fong.service.MemberService;
import SeekerPoolBoot.fong.util.RandCodeUtil;
import SeekerPoolBoot.fong.util.SendEmailTextUtil;
import SeekerPoolBoot.fong.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Boolean registerMember(Member member) {

		Boolean flag = memberRepository.existsByMemAccount(member.getMemAccount());
		if (flag) {
			// 有重複帳號
			return false;
		}
		// 無重複帳號
		memberRepository.save(member);
		return true;
	}

	@Override
	public Member loginMember(Member member) {
		Member memberLogin = memberRepository.findByMemAccountAndMemPassword(member.getMemAccount(),
				member.getMemPassword());
		return memberLogin;
	}

	@Override
	public Member getMemberByMemyId(Integer memId) {
		return memberRepository.getByMemId(memId);
	}

	@Override
	public void updateMember(Member member) {
		memberRepository.save(member);
	}

	@Override
	public String sendCheckCode(Member member, String contextPath) {
		// 得到隨機變數
		String randomCode = RandCodeUtil.getRandomCode(4);

		String emailTo = member.getMemEmail();
		String emailSubject = "【Seeker's Pool】 驗證碼";
		String name = member.getMemName();
		String messageText = name + " 您好，您的驗證碼為: " + randomCode
				+ " <br> 請立即返回驗證頁面進行帳號驗證，驗證碼有效時間為 20秒  <br> http://localhost:8080" + contextPath
				+ "/front-end/member/member/checkcode.html ";

		// 開立多執行續寄信(邊跳轉頁面邊寄信 效率更快)
		Thread t1 = new Thread(() -> SendEmailTextUtil.sendMail(emailTo, emailSubject, messageText));
		t1.start();
		return randomCode;
	}

}
