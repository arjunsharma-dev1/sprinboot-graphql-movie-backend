package com.arjun.netflix.clone.netflixclonegraphql;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    @MutationMapping
    public boolean addActorToMovie(
            @NonNull @Argument String movieId,
            @NonNull @Argument String actorId) {
        var actorOptional = actorRepository.findById(actorId);
        var movieOptional = movieRepository.findById(movieId);

        if (actorOptional.isEmpty() || movieOptional.isEmpty()) {
            return false;
        }
        var movie = movieOptional.get();
        boolean isAdded = movie.addActor(actorOptional.get());
        if(isAdded) {
            movieRepository.save(movie);
        }
        return isAdded;

    }

    @MutationMapping
    public String createActor(@Argument String name,
                              @Argument LocalDate dob,
                              @Argument List<String> movies) {
        var actor = new Actor()
                .setDob(dob)
                .setName(name);
        if (!CollectionUtils.isEmpty(movies)) {
//            TODO: report if movie(s) given not present
            var moviesFetched = movieRepository.findAllById(movies);
            if (moviesFetched.size() < movies.size()) {
                return null;
            }
            actor.addMovies(moviesFetched);
//            movieRepository.saveAll(moviesFetched);
        }
        actorRepository.save(actor);
        return actor.getId();
    }

    @MutationMapping
    public Rating addRating(@Argument String movieId,
                              @Argument short scale,
                              @Argument float rate) {
        var movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            return null;
        }
        var movie = movieOptional.get();
        if (rate > scale) {
            return null;
        }
        movie.getRating().setRate(rate).setScale(scale);
        movieRepository.save(movie);
        return movie.getRating();
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
