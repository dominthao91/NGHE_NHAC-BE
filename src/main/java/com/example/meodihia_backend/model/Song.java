package com.example.meodihia_backend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String file;
    private String avatar;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "song_singer", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "singer_id"))
    private List<Singer> singers;

    private String musician;
    private Long count;
    private Long countLike;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "song", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Likes> likesList = new ArrayList<>();
      @OneToMany(cascade = CascadeType.ALL, mappedBy = "song", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> commentList;
      @ManyToMany(fetch = FetchType.LAZY, targetEntity = Playlist.class)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Playlist> playlists;
}