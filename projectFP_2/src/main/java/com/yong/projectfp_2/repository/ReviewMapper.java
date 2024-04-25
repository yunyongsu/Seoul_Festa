package com.yong.projectfp_2.repository;

import com.yong.projectfp_2.model.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

	List<Review> getReviewsByFestivalId(int ftNum);
	void insertReview(Review review);
	void deleteReview(int reviewNum);
	Double averageRating(int ftNum);
};
