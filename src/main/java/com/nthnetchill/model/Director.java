package com.nthnetchill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "director")
public class Director extends Base {
    private String name;
    private Date dateOfBirth;
    private String information;
    private String avatarUrl;

    @ManyToMany(mappedBy = "directors")
    @JsonIgnore
    Set<Movie> movies;
}
