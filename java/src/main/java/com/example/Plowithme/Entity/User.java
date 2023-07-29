package com.example.Plowithme.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private LocalDateTime create_date; //임시
    @Column
    private String birth;
    @Column
    private String profile_image;
    @Column
    private String role;
    @Column
    private String region;

//    @Embedded
//    private Region region;

    //게시글
//    @OneToMany(mappedBy = "member")
//    private List<Board> boards = new ArrayList<>();
//

}
