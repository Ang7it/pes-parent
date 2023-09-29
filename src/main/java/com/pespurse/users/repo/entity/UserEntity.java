package com.pespurse.users.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity(name = "user")
@Table(name = "pes_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private LocalDate userDob;
    private Double userRating;
    private Integer userType; //refer UserType enum
    private String userCountry;
    private String userAvatar;
    private String userGameId;
}
