package com.example.Plowithme.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postid;

    @Column
    private String post_title;

    @Column
    private String post_contents;

    @Column
    private String post_create_date;

    @Column
    private String post_update_date;

    @Column
    private String post_category;

    @Column
    private String post_image;

    @Column
    private String post_image_path;
}
