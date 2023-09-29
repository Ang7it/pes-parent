package com.pespurse.match.service;

import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.match.web.handler.MatchHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MatchService {
    private final MatchHandler matchHandler;

    public SaveMatchDTO saveNewMatch(SaveMatchDTO saveMatchDTO) {
        return matchHandler.createNewMatch(saveMatchDTO).getData();
    }

    public List<SaveMatchDTO> getLeagueFixtures(Long leagueId, Long currentSeasonId) {
        List<SaveMatchDTO> fixtures= matchHandler.getLeagueFixtures(leagueId, currentSeasonId);
        fixtures.sort(Comparator.comparing(SaveMatchDTO::getScheduledTime));
        return fixtures;
    }
}
