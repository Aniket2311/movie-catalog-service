package com.aniket.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aniket.moviecatalogservice.models.CatalogItem;
import com.aniket.moviecatalogservice.models.Movie;
import com.aniket.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping(path = "/catalog")
public class MovieCatalogResource {

    private RestTemplate restTemplate;

    @Autowired
    public MovieCatalogResource(RestTemplate restTemplate) {

    	this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating userRating = 
        		restTemplate.getForObject("http://RATINGS-DATA-SERVICE/ratings/users/" + userId, UserRating.class);

        if(userRating == null || userRating.getUseRatings().isEmpty()) {
        	
        	throw new IllegalArgumentException("User Ratings is empty...");
        }
        
        return userRating
        		.getUseRatings()
                .stream()
                .map(rating -> {

                    Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class);

                    return new CatalogItem(movie.getName(), "Kolar Gold Fields Part 1", rating.getRating());
                })
                .collect(Collectors.toList());
    }
}
