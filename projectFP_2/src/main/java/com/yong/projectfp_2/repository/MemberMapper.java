package com.yong.projectfp_2.repository;

import com.yong.projectfp_2.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

	Member selectOneMember(int userNum);
	List<Member> MemberList();
	void insertMember(Member member);
	void deleteMember(int userNum);
	void updateMember(Member member);
	Member selectLogin(Member member);
}
