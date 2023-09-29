package com.pespurse.users.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
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
    private UserStatisticsDTO userStatisticsDTO;

}
