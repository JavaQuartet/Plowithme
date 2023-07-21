package com.example.Plowithme.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.service.spi.InjectService;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String user_email;

    @Column
    private String user_password;

    @Column
    private String user_name;

    @Column
    private String user_location;
}
