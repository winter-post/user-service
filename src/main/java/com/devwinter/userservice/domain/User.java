package com.devwinter.userservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "USERS_UK",
                columnNames = {"email", "password"}
        )
)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String email;
    @Column(nullable = false)
    private String password;

    @Builder
    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
