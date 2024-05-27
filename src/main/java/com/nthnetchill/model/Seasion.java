package com.nthnetchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "seasion")
public class Seasion extends Base{
    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    private String description;

    @OneToMany(mappedBy = "seasion")
    Set<Episode> episodes;
}
