package com.example.Plowithme.Entity;
import com.example.Plowithme.Entity.Region;
//import com.example.Plowithme.Entity.Board;
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

    private String email;
    private String password;
    private String name;
    private LocalDateTime create_date; //임시
    private String birth;
    private String profile_image;
    private String role;

    @Embedded
    private Region region;

    //게시글
//    @OneToMany(mappedBy = "member")
//    private List<Board> boards = new ArrayList<>();
//

}
