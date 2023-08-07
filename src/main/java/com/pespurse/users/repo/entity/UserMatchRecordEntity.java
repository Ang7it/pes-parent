package com.pespurse.users.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "userMatchRecord")
@Table(name = "user_match_record")
@AllArgsConstructor
@NoArgsConstructor
public class UserMatchRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;
    Long winMatchId; //this stores the match id of the users biggest win
    Long lostMatchId; //this does the sam but for a lost match.

}
