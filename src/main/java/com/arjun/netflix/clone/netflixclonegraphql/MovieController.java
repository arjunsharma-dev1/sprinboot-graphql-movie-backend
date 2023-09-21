package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

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

    @SchemaMapping
    public MovieMeta movieMeta(Movie movie) {
        return movieMetaRepository.getReferenceById(movie);
    }

    @SchemaMapping
    public List<Actor> getActorOfMovie(Movie movie) {
        return actorRepository.getByMovie(movie);
    }
}
