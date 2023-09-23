package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, String> {
    List<Actor> getByMovies(Movie movie);
    Optional<Actor> getByMoviesAndId(Movie movie, String id);
}
