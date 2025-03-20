package com.movie.talk.dto;

public class Review {
    private int id;
    private String userId;
    private String nickname;
    private int rating;
    private String content;
    private int movieId;
    
    public Review(int id, String userId, String nickname, int rating, String content, int movieId) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.rating = rating;
        this.content = content;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
