package com.nthnetchill.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_single")
public class MovieSingle extends Base {

    @OneToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    Movie movie;

    private String videoUrl;
    @Column(nullable = true)
    private String description;
}
