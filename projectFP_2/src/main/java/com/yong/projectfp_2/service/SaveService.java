package com.yong.projectfp_2.service;

import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.Save;
import com.yong.projectfp_2.repository.SaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaveService {

    @Autowired
    private SaveMapper saveMapper;

    public List<Save> saveList(int userNum) {

        return saveMapper.saveList(userNum);
    }

    @Transactional
    public void insertSave(int userNum, int ftNum) {
    	saveMapper.insertSave(userNum, ftNum);
    }

    @Transactional
    public void deleteSave(int saveNum) {
    	saveMapper.deleteSave(saveNum);
    }

    public List<Festival> getSavedFestivals(int userNum) {
        return saveMapper.getSavedFestivals(userNum);
    }

}
