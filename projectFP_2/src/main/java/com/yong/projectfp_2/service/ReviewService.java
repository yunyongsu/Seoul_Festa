package com.yong.projectfp_2.service;

import com.yong.projectfp_2.model.Review;
import com.yong.projectfp_2.repository.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public List<Review> getReviewsByFestivalId(int ftNum) {
            return reviewMapper.getReviewsByFestivalId(ftNum);
        }
    public void insertReview(Review review) {
        reviewMapper.insertReview(review);
    }

    public Double averageRating(int ftNum){
        return reviewMapper.averageRating(ftNum);
    }

    public void deleteReview(int reviewNum){reviewMapper.deleteReview(reviewNum);}
    }

