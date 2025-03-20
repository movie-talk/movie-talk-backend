package com.movie.talk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.talk.dto.Review;
import com.movie.talk.dto.User;
import com.movie.talk.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> insertReview(@RequestBody Review review, @RequestParam("movieId") int movieId, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        try {
            if (session != null) {
                User user = (User) session.getAttribute("user");

                if (user == null) {
                    return ResponseEntity.status(401).body("로그인 후 이용해 주세요.");
                }

                String id = user.getId();
                review.setUserId(id);
                review.setMovieId(movieId);

                reviewService.insertReview(review);

                return ResponseEntity.ok().body("리뷰 등록 성공");
            } else {
                return ResponseEntity.status(400).body("세션이 유효하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류");
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable String reviewId, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        try {
            if (session != null) {
                User user = (User) session.getAttribute("user");

                if (user == null) {
                    return ResponseEntity.status(401).body("로그인 후 이용해 주세요.");
                }

                boolean isDeleted = reviewService.deleteReview(reviewId, user.getId());

                if (isDeleted) {
                    return ResponseEntity.ok().body("리뷰 삭제 성공");
                } else {
                    return ResponseEntity.status(403).body("본인이 작성한 리뷰만 삭제 가능합니다.");
                }
            } else {
                return ResponseEntity.status(400).body("세션이 유효하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류");
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable String reviewId, @RequestBody Review review, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        try {
            if (session != null) {
                User user = (User) session.getAttribute("user");

                if (user == null) {
                    return ResponseEntity.status(401).body("로그인 후 이용해 주세요.");
                }

                review.setUserId(user.getId());
                boolean isUpdated = reviewService.updateReview(reviewId, review, user.getId());

                if (isUpdated) {
                    return ResponseEntity.ok().body("리뷰 수정 성공");
                } else {
                    return ResponseEntity.status(404).body("리뷰를 찾을 수 없습니다.");
                }
            } else {
                return ResponseEntity.status(400).body("세션이 유효하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류");
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getReviewsByUserId(@PathVariable String userId) {
        try {
            List<Review> reviews = reviewService.getReviewsByUserId(userId);
            if (reviews != null && !reviews.isEmpty()) {
                return ResponseEntity.ok(reviews);
            } else {
                return ResponseEntity.status(404).body("아직 리뷰가 없습니다. 첫 리뷰를 작성해보세요!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류");
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Object> getReviewsByMovieId(@PathVariable String movieId) {
        try {
            List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
            if (reviews != null && !reviews.isEmpty()) {
                return ResponseEntity.ok(reviews);
            } else {
                return ResponseEntity.status(404).body("아직 리뷰가 없습니다. 첫 리뷰를 작성해보세요!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류");
        }
    }
}
