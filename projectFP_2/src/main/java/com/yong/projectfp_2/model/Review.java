package com.yong.projectfp_2.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Review {
    private int reviewNum;
    private String reviews;
    private String regdate;
    private int userNum;
    private Integer ftNum;
    private String writer;
    private int rating;

}
