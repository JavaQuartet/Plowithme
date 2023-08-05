package com.example.Plowithme.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "User")
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
    private String nickname;

    @Column
    @CreatedDate
    private LocalDateTime create_date;

    @Column
    private String birth;

    @Embedded
    @Column
    private Profile profile_image;

    @Column
    private String role;

    @Column
    private String region;

//    //비밀번호 암호화
//    public void encodePassword(PasswordEncoder passwordEncoder){
//        this.password = passwordEncoder.encode(password);
//    }


//  지역 깊이 나눌건지에 따라 추가할 것
//    @Embedded
//    private Region region;

    //게시글과 연관관계 생성 - User 엔티티 코드
//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();
//
    //Board 엔티티에 추가해야하는 연관관계 코드 참고하세욥~~~
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//

}
