package com.nthnetchill.model;

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
@Table(name = "episode")
public class Episode extends Base{
    @ManyToOne
    @JoinColumn(name = "season_id")
    @JsonIgnore
    Season season;

    @Column(nullable = true)
    private String name;
    private int number;
    private String videoUrl;
    @Column(nullable = true)
    private String description;
}
