package com.pespurse.users.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "userMetadata")
@Table(name = "user_match_record")
@AllArgsConstructor
@NoArgsConstructor
public class UserMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;

}
