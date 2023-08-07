package com.pespurse.users.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity(name = "user")
@Table(name = "pes_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String userEmail;
    String userPhoneNumber;
    Date userDob;
    Double userRating;
    Integer userType; //refer UserType enum
    String userCountry;
    String userAvatar;
    String userGameId;
}
