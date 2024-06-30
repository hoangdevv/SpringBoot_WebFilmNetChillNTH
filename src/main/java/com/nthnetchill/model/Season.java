package com.nthnetchill.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "season")
public class Season extends Base{
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    Movie movie;

    private String title;
    private int seasonNumber;
    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "season")
    Set<Episode> episodes;
}
