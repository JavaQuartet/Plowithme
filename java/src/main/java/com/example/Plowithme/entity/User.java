package com.example.Plowithme.entity;

import com.example.Plowithme.exception.custom.FileException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class User implements UserDetails {

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> BoardList=new ArrayList<>();

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


//    @OneToMany(mappedBy = "user")
//    private List<Message> messages = new ArrayList<>();
//

//    public boolean equals(Object object) {
//        if (this == object)
//            return true;
//        if (object == null || getClass() != object.getClass())
//            return false;
//        JwtUser that = (JwtUser) object;
//        return Objects.equals(id, that.id);
//    }





    public void updatePassword(String password) {
        this.password = password;
    }
//    public void updatePassword(PasswordEncoder passwordEncoder, String password){
//        this.password = passwordEncoder.encode(password);
//    }

    public String getProfileUrl(String profile) {
    try {
        return Paths.get("uploads/profiles").resolve(this.profile).toUri().toURL().toString();

        }catch (Exception e){
        throw new FileException("파일을 조회할 수 없습니다.");}
    }

}

