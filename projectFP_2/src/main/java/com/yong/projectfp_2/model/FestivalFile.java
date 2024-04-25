package com.yong.projectfp_2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FestivalFile {
    private int fileNum;
    private int ftNum;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}
