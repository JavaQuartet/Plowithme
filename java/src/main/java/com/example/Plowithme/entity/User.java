package com.example.Plowithme.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String birth;

    private String name;


    @Column(nullable = false)
    @Embedded
    private Region region;

    private String nickname;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime create_date;

    @Column
    private String profile;

    @Column
    private String introduction;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column
    private int class_count;

    @Column
    private double class_distance;


    //권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public boolean equals(Object object) {
//        if (this == object)
//            return true;
//        if (object == null || getClass() != object.getClass())
//            return false;
//        JwtUser that = (JwtUser) object;
//        return Objects.equals(id, that.id);
//    }

    //현재 유저의 id
    public Long curentUserId() {
        return id;
    }

    //게시글과 연관관계 생성 - User 엔티티 코드
//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();
//
    //Board 엔티티에 추가해야하는 연관관계 코드 참고하세욥~~~
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//



    public void updatePassword(String password){
        this.password = password;
    }
//    public void updatePassword(PasswordEncoder passwordEncoder, String password){
//        this.password = passwordEncoder.encode(password);
//    }
}

