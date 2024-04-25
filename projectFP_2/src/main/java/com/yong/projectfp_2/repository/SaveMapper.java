package com.yong.projectfp_2.repository;

import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.Save;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SaveMapper {
	
	List<Save> saveList(int userId);
	void insertSave(Save save);
	void deleteSave(int saveNum);
	void insertSave(int userNum, int ftNum);
	List<Festival> getSavedFestivals(int userNum);
}
