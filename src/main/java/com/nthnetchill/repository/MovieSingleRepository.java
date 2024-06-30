package com.nthnetchill.repository;

import com.nthnetchill.model.MovieSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSingleRepository extends JpaRepository<MovieSingle, Long> {
}
