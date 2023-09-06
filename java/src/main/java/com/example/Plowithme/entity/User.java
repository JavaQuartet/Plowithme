package com.example.Plowithme.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; //이메일(로그인 아이디)

    @Column(nullable = false)
    private String password; //비밀번호

    private String birth; //생일

    private String name; //이름

    @Column(nullable = false)
    @Embedded
    private Region region; //지역

    private String nickname; //닉네임

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime create_date; //생성 날짜

    @Column
    private String profile; //프로필 url

    @Column
    private String introduction; //소개

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>(); //역할(ROEL_USER or ROEL_ADMIN)

    @Column
    private int class_count; //전체 참여 모임 횟수

    @Column
    private double class_distance; //전체 참여 모임 거리

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> BoardList=new ArrayList<>();

    @OneToMany(mappedBy = "user",  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ClassEntity> classEntities =new ArrayList<>();


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


    public void updatePassword(String password) {
        this.password = password;
    }


}

