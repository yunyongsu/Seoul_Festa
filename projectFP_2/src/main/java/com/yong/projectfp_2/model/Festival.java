package com.yong.projectfp_2.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Festival {
	 private int ftNum;
	 private String title;
	 private String startdate;
	 private String enddate;
	 private String place;
	 private String price;
	 private List<FestivalFile> fileList;
}
