package com.yong.projectfp_2.service;

import com.yong.projectfp_2.common.FileUtils;
import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.FestivalFile;
import com.yong.projectfp_2.repository.FestivalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class FestivalService {

    @Autowired
    private FestivalMapper festivalMapper;
    @Autowired
    private FileUtils fileUtils;

    public Festival selectOneFT(int ftNum) throws Exception {
        return festivalMapper.selectOneFT(ftNum);
    }

    public List<Festival> FTList() {
        return festivalMapper.FTList();
    }

    @Transactional
    public void insertFT(Festival festival, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        festivalMapper.insertFT(festival);
        List<FestivalFile> list = fileUtils.parseFileInfo(festival.getFtNum(), multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(list) == false) {
            festivalMapper.insertBoardFileList(list);

        }
    }

    @Transactional
    public void deleteFT(int ftNum) {
        festivalMapper.deleteFT(ftNum);
    }

    @Transactional
    public void updateFT(Festival festival) {
        festivalMapper.updateFT(festival);
    }

    public int getFestivalsByCountMonth(int month) {
        return festivalMapper.getFestivalsByCountMonth(month);
    }

    public int getFestivalCount() {
        return festivalMapper.getFestivalCount();
    }

    public List<Festival> getFestivalsByMonthPaging(int month, int page, int size) {
        int offset = (page - 1) * size;
        return festivalMapper.getFestivalsByMonthPaging(month, offset, size);
    }

    public List<Festival> getFestivalsPaging(int page, int size) {
        int offset = (page - 1) * size;
        return festivalMapper.getFestivalsPaging(offset, size);
    }


    public List<Festival> getFestivalsByStartDate(LocalDate startdate) {
        return festivalMapper.getFestivalsByStartDate(startdate);
    }

    public String getFestivalAddress(int ftNum) {
        return festivalMapper.getFestivalAddress(ftNum);
    }
}