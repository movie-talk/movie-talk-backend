package com.movie.talk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movie.talk.dao.ReviewDao;
import com.movie.talk.dto.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    public void insertReview(Review review) {
        reviewDao.insertReview(review);
    }
    
    public boolean deleteReview(String reviewId, String userId) {
        Review review = reviewDao.findReviewById(reviewId);

        if (review == null) {
            return false;
        }

        if (!review.getUserId().equals(userId)) {
            return false;
        }

        reviewDao.deleteReview(reviewId, userId);
        return true;
    }


    public boolean updateReview(String reviewId, Review review, String userId) {
        Review existingReview = reviewDao.findReviewById(reviewId);

        if (existingReview == null) {
            return false;
        }

        if (!existingReview.getUserId().equals(userId)) {
            return false;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", reviewId);
        params.put("content", review.getContent());
        params.put("rating", review.getRating());
        params.put("userId", userId);

        reviewDao.updateReview(params);
        return true;
    }


    public List<Review> getReviewsByUserId(String userId) {
        try {
            return reviewDao.selectReviewsByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("리뷰 조회 중 오류 발생", e);
        }
    }
    
    public List<Review> getReviewsByMovieId(String movieId) {
        try {
            return reviewDao.selectReviewsByMovieId(movieId);
        } catch (Exception e) {
            throw new RuntimeException("리뷰 조회 중 오류 발생", e);
        }
    }

    public List<Review> getAllReviews() {
        return reviewDao.selectAllReviews();
    }
}
