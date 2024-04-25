package com.yong.projectfp_2.repository;

import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.FestivalFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FestivalMapper {
	
	Festival selectOneFT(int ftNum);
	List<Festival> FTList();
	void insertFT(Festival festival);

	void deleteFT(int ftNum);
	void updateFT(Festival festival);
	int getFestivalsByCountMonth(int month);

	int getFestivalCount();
	List<Festival> getFestivalsByMonthPaging(int month, int page, int size);

	List<Festival> getFestivalsPaging(int page, int size);

	List<String> selectBoardFileList(int ftNum);
	void insertBoardFileList(List<FestivalFile> list);
	List<Festival> getFestivalsByStartDate(LocalDate startdate);

	String getFestivalAddress(int ftNum);
}
