package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

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
        return movieRepository.getReferenceById(id);
    }

//    @QueryMapping
//    public Actor getActorById(@Argument String actorId) {
//        return actorRepository.getReferenceById(actorId);
//    }
    @SchemaMapping
    public MovieMeta movieMeta(String movieId) {
        return movieMetaRepository.getReferenceByMovieId(movieId);
    }

    @SchemaMapping
    public List<Actor> actors(Movie movie, @Argument String id) {
        if (id != null && !id.isBlank()) {
            return List.of(actorRepository.getByMoviesAndId(movie, id));
        }
        return actorRepository.getByMovies(movie);
    }
}
