package com.yong.projectfp_2.model;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Member {
	private int userNum;
	private String id;
	private String pw;
	private String email;
	private String regdate;
}
