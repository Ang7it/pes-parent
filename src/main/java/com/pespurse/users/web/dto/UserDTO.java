package com.pespurse.users.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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
