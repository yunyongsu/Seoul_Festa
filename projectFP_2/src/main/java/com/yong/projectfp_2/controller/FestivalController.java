package com.yong.projectfp_2.controller;

import com.yong.projectfp_2.model.Review;
import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.Member;
import com.yong.projectfp_2.repository.FestivalMapper;
import com.yong.projectfp_2.service.ReviewService;
import com.yong.projectfp_2.service.FestivalService;
import com.yong.projectfp_2.service.SaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class FestivalController {

    @Autowired
    private FestivalService festivalService;
    @Autowired
    private SaveService saveService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FestivalMapper festivalMapper;


    @GetMapping("/festival_insert")
    public String showFestivalRegistrationPage() {
        return "festival_insert";
    }

    @PostMapping("/insertFT")
    public String insertFT(Festival festival, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        festivalService.insertFT(festival, multipartHttpServletRequest);
        return "redirect:/"; // 등록이 성공하면 홈 페이지로 리다이렉트합니다.
    }
    @PostMapping("/deleteFT")
    public String deleteFT(int ftNum){
        festivalService.deleteFT(ftNum);
        return "redirect:/festivalList";
    }

    @GetMapping("/festivalList")
    public String showFestivalListPage(@RequestParam(name = "month", required = false) Integer month,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "3") int size, Model model,
                                       @RequestParam(name = "ftNum", required = false) Integer ftNum) {

        if (page < 1) {
            page = 1;
        }

        int totalFestivals;
        List<Festival> festivals;
        if (month != null && month >= 1 && month <= 12) {
            // 특정 월에 해당하는 축제 수를 가져옵니다.
            totalFestivals = festivalService.getFestivalsByCountMonth(month);

            // 해당 월의 축제 목록을 페이징하여 가져옵니다.
            festivals = festivalService.getFestivalsByMonthPaging(month, page, size);
        } else {
            // 전체 축제 수를 가져옵니다.
            totalFestivals = festivalService.getFestivalCount();
            // 전체 축제 목록을 페이징하여 가져옵니다.
            festivals = festivalService.getFestivalsPaging(page, size);
            //페이징을 month값일때만 줬기때문에 전체목록에서는 null값이 나왔다. 그걸 피하기 위한 임시방편
            month = 0;
        }

        if (totalFestivals == 0) {
            page = 1;
        }
        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalFestivals / size);


        // 모델에 필요한 정보 추가
        model.addAttribute("month", month);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("festivals", festivals);
        return "festivalList";
    }

    @GetMapping("/festival_Detail")
    public String showFestivalDetailPage(@RequestParam(name = "ftNum") Integer ftNum, Model model) throws Exception {
        Festival festival = festivalService.selectOneFT(ftNum);
        List<Review> reviewList = reviewService.getReviewsByFestivalId(ftNum);
        double averageRating = reviewService.averageRating(ftNum);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("festival", festival);
        // 상세 페이지로 이동
        return "festival_Detail";
    }

    @PostMapping("/insertReview")
    public String insertReview(Review review, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println(review.getFtNum());
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember != null) {
            review.setUserNum(loginMember.getUserNum());
            review.setWriter(loginMember.getId());
            reviewService.insertReview(review);
            redirectAttributes.addAttribute("ftNum", review.getFtNum());
            return "redirect:/festival_Detail";
        } else {
            return "redirect:/login";
        }
    }

    //축제목록에서 저장버튼을 눌렀을 때
    @PostMapping("/saveFestival")
    public String saveFestival(int ftNum, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember != null) {
            saveService.insertSave(loginMember.getUserNum(), ftNum); // member 번호와 festival 번호 전달하여 save에 저장
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    //저장된 축제 목록
    @GetMapping("/savedFestival")
    public String getSavedFestivals(Model model, HttpSession session) {
        // 세션에서 로그인된 회원 정보 가져오기
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember != null) {
            // 세션에서 가져온 회원의 식별자를 기반으로 저장된 축제 목록 조회
            List<Festival> savedFestivals = saveService.getSavedFestivals(loginMember.getUserNum());
            // 조회된 축제 목록을 모델에 추가하여 뷰로 전달
            model.addAttribute("savedFestivals", savedFestivals);
            // 저장된 축제 목록 페이지로 이동
            return "savedFestival";
        } else {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
    }
    @GetMapping("/address")
    @ResponseBody
    public String getFestivalAddress(int ftNum) {
        // 축제 주소를 가져오는 MyBatis 쿼리 호출
        String address = festivalService.getFestivalAddress(ftNum);
        return address;
    }

}
