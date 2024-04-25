package com.yong.projectfp_2.service;

import com.yong.projectfp_2.model.Member;
import com.yong.projectfp_2.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Member selectOneMember(int userNum) {
        return memberMapper.selectOneMember(userNum);
    }
    
    public Member SelectLogin(Member member) {
        return memberMapper.selectLogin(member);
    }

    public List<Member> MemberList() {
        return memberMapper.MemberList();
    }

    @Transactional
    public void insertMember(Member member) {
    	memberMapper.insertMember(member);
    }
    
    @Transactional
    public void deleteMember(int userNum) {
    	memberMapper.deleteMember(userNum);
    }
    
    @Transactional
    public void updateMember(Member member) {
    	memberMapper.updateMember(member);
    }

}
