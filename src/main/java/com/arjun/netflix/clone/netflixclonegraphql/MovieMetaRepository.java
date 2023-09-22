package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieMetaRepository extends JpaRepository<MovieMeta, String> {
    MovieMeta getReferenceByMovieId(String movieId);
}
