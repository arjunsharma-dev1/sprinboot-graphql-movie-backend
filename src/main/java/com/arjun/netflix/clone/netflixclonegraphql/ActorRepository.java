package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, String> {
    List<Actor> getByMovie(Movie movie);
}