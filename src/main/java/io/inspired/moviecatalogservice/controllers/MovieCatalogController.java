package io.inspired.moviecatalogservice.controllers;


import io.inspired.moviecatalogservice.models.CatalogItem;
import io.inspired.moviecatalogservice.models.Movie;
import io.inspired.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public WebClient.Builder webClientBuilder;

    @Value("${movieInfoUrl}")
    private String movieInfoUrl;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("12345",4),
                new Rating("1456",5)
        );
       return ratings.stream().map(rating ->
       {
           //ToDo: using rest templates to make api calls.
           Movie movie =   restTemplate.getForObject( "http://localhost:8081/movies/"+ rating.getMovieId(), Movie.class);
           //TODO: Webclient interface using the reactive programming aspect of making api calls in code.
    /*    Movie movie = webClientBuilder.build()
                   .get()
                   .uri(movieInfoUrl+rating.getMovieId())
                   .retrieve()
                   .bodyToMono(Movie.class)
                   .block(); */

          return new CatalogItem(movie.getName(),"Test",rating.getRating());
       }).collect(Collectors.toList());
        //get all movie ratings
        //put them all together


//        return Collections.singletonList(
//                new CatalogItem("Transformers", "Test", 4)
//        );
    }
}
