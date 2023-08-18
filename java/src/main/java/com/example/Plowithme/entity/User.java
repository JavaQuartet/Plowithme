package com.example.Plowithme.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 30)
    private String birth;

    @Column(length = 30)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Region region;

    @Column(length = 30)
    private String nickname;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime create_date;

    @Column
    private String profile;


    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    //권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    @Override
    public String getUsername() {
        return email;
    }

    @Column
    private String region;

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
