package com.course_platform.courses.entity;

import com.course_platform.courses.auth.UserPrincipal;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {
    @Column
    private String type;
    @Id
    private String token;
    @Column
    private Date expiredAt;
    @Column
    private boolean isExpired;
    @ManyToOne
    private UserEntity user;
}
