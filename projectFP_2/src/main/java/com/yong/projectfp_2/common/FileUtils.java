package com.yong.projectfp_2.common;

import com.yong.projectfp_2.model.Festival;
import com.yong.projectfp_2.model.FestivalFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {


    public List<FestivalFile> parseFileInfo(int ftNum, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<FestivalFile> fileList = new ArrayList<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        //경로를 하드코딩하여 사용
        String path = "D:\\_dev\\intellij\\projectFP_2\\images\\"+current.format(format);
        File file = new File(path);
        if(file.exists() == false){
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;

        while(iterator.hasNext()){

            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list){
                if(multipartFile.isEmpty() == false){
                    contentType = multipartFile.getContentType();
                    if(ObjectUtils.isEmpty(contentType)){
                        break;
                    }
                    else{

                        if(contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";

                        }
                        else if(contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        }
                        else if(contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        }
                        else{
                            break;
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                    FestivalFile festivalFile = new FestivalFile();
                    festivalFile.setFtNum(ftNum);
                    festivalFile.setFileSize(multipartFile.getSize());
                    festivalFile.setOriginalFileName(multipartFile.getOriginalFilename());
                    festivalFile.setStoredFilePath(path + "/" + newFileName);
                    fileList.add(festivalFile);

                    file = new File(path + "/" + newFileName);
                    System.out.println("1번------"+file);
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }
}
