package com.pespurse.players.web.dto;

import com.pespurse.players.repo.entity.enums.CardType;
import com.pespurse.players.repo.entity.enums.Position;
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
    Position position; //cf, cmf, gk
    CardType cardType;  //featured, epic, highlight, standard, big time
    String tag;  //can be used as a custom system generated tag
    Integer version;  //can be used to track the version of the player
    String playerImage;
}
