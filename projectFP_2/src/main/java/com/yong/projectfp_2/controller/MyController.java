package com.yong.projectfp_2.controller;

import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.Member;
import com.yong.projectfp_2.service.CalendarService;
import com.yong.projectfp_2.service.FestivalService;
import com.yong.projectfp_2.service.MemberService;
import com.yong.projectfp_2.service.SaveService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

@Controller
public class MyController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private CalendarService calendarService;
	@Autowired
	private FestivalService festivalService;

	@GetMapping("/")
	public String home(Model model, @RequestParam(name = "year", required = false, defaultValue = "2024") int year,
					   @RequestParam(name = "month", required = false, defaultValue = "4") int month) {

		// 달력 생성
		List<List<String>> weeks = calendarService.generateCalendar(year, month);
		model.addAttribute("weeks", weeks);

		// 이전 월과 다음 월을 계산하여 모델에 추가
		model.addAttribute("prevYear", year);
		model.addAttribute("prevMonth", month - 1 < 1 ? 12 : month - 1);
		model.addAttribute("nextYear", year);
		model.addAttribute("nextMonth", month + 1 > 12 ? 1 : month + 1);

		// 현재 년월을 모델에 추가
		model.addAttribute("currentYear", year);
		model.addAttribute("currentMonth", month);

		model.addAttribute("festival_card", festivalService.FTList());

		LocalDate startDate = LocalDate.of(year, month, 1);
		List<Festival> festivals = festivalService.getFestivalsByStartDate(startDate);
		model.addAttribute("festivals", festivals);

		return "main";
	}

	@GetMapping("/sign")
	public String showSignupPage() {
		return "sign";
	}


	@GetMapping("/member_delete")
	public String showDeletePage(){
		return "member_delete";
	}

	@PostMapping("/member_join")
	public String memberJoin(Member member) {
		memberService.insertMember(member);
		return "login";
	}


	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/memberLogin")
	public String memberLogin(Member member, HttpSession session) {
		Member loginMember = memberService.SelectLogin(member);
		if (loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			return "redirect:/";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginMember"); // 세션에서 로그인 정보 삭제
		session.invalidate(); // 세션 무효화
		return "redirect:/"; // 로그아웃 후 로그인 페이지로 이동
	}


	@GetMapping("/memberlist")
	public String showMemberListPage(Model model) {
		model.addAttribute("members", memberService.MemberList());
		return "memberlist";
	}

	@GetMapping("/member_update")
	public String showUpdatePage(HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
		}
		// 로그인한 사용자의 정보를 모델에 추가하여 업데이트 페이지로 전달
		model.addAttribute("member", loginMember);
		return "member_update";
	}

	@PostMapping("/member_update")
	public String memberUpdate(@ModelAttribute Member member, HttpSession session){
		// 세션에서 로그인한 사용자 정보 가져오기
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
		}
		// 로그인한 사용자의 정보로 업데이트
		loginMember.setId(member.getId());
		loginMember.setPw(member.getPw());
		loginMember.setEmail(member.getEmail());
		// 회원 정보 업데이트
		memberService.updateMember(loginMember);
		return "redirect:/";
	}

	@PostMapping("/member_delete")
	public String memberDelete(HttpSession session){
		// 세션에서 로그인한 사용자 정보 가져오기
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
		}
		// 회원 정보 삭제
		memberService.deleteMember(loginMember.getUserNum());
		// 세션에서 로그인 정보 삭제
		session.removeAttribute("loginMember");
		session.invalidate(); // 세션 무효화
		return "redirect:/";
	}

	@PostMapping("/memberlist_delete")
	public String memberlistDelete(int userNum){
		// 회원 정보 삭제
		memberService.deleteMember(userNum);
		return "redirect:/";
	}

	@PostMapping("/memberlist_update")
	public String memberlistUpdate(Member member) {
		// 회원 정보 업데이트
		memberService.updateMember(member);
		return "memberList";
	}


}

