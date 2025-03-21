package com.movie.talk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.movie.talk.dto.Review;

@Mapper
public interface ReviewDao {

    void insertReview(Review review);

    Review findReviewById(String reviewId);

    void deleteReview(String reviewId, String userId);

    void updateReview(Map<String, Object> params);

    List<Review> selectReviewsByUserId(String userId);
    
    List<Review> selectReviewsByMovieId(String movieId);

    List<Review> selectAllReviews();
}
