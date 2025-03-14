package com.movie.talk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieService {

    @Value("${TMDB_API_KEY}")
    private String apiKey;

    private static final String TMDB_API_URL = "https://api.themoviedb.org/3/movie/now_playing";

    public String getNowPlayingMovies() {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_URL)
                .queryParam("api_key", apiKey)
                .queryParam("language", "ko-KR")
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
