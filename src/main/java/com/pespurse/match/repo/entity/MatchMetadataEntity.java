package com.pespurse.match.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "matchMetadata")
@Table(name = "match_metadata")
@AllArgsConstructor
@NoArgsConstructor
public class MatchMetadataEntity {
    @Id
    Long id;
    Long matchId;


}
