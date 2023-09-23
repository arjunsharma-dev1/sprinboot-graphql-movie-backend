package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieMetaRepository movieMetaRepository;
    @Autowired
    private ActorRepository actorRepository;

    @QueryMapping
    public Movie movieById(@Argument String id) {
        var movieOptional = movieRepository.findById(id);
        return movieOptional.orElse(null);
    }

    @QueryMapping
    public List<Actor> getActors() {
        return actorRepository.findAll();
    }

    @QueryMapping
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @SchemaMapping
    public MovieMeta movieMeta(String movieId) {
        return movieMetaRepository.getReferenceByMovieId(movieId);
    }

    @MutationMapping
    public String createMovie(@Argument List<String> genre,
                              @Argument String title,
                              @Argument LocalDate releasedOn,
                              @Argument List<String> actorIds) {
        var actors = new ArrayList<Actor>();
        if (!CollectionUtils.isEmpty(actorIds)) {
             actorRepository.findAllById(actorIds);
        }
        if(movieRepository.existsByMetaTitle(title)) {
            return "-1";
        }

        var movieMeta = new MovieMeta()
                .setGenre(genre)
                .setTitle(title)
                .setReleasedOn(releasedOn);

        var movie = new Movie()
                .setActors(actors)
                .setMeta(movieMeta)
                .setRating(
                        new Rating()
                                .setScale(5.0f)
                                .setRate(0.0f));
        movieMeta.setMovie(movie);
        movieRepository.save(movie);
        return movie.getId();
    }

    @SchemaMapping
    public List<Actor> actors(Movie movie, @Argument String id) {
        if (id != null && !id.isBlank()) {
            var actor = actorRepository.getByMoviesAndId(movie, id);
            return actor.map(List::of).orElse(Collections.emptyList());
        }
        return actorRepository.getByMovies(movie);
    }
}
