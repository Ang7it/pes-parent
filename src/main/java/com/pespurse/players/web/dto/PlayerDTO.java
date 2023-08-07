package com.pespurse.players.web.dto;

import com.pespurse.players.repo.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    Long id;
    String name;
    String nationality;
    String club;
    Integer rating;
    CardType cardType;
    String tag;
    Integer version;
}
