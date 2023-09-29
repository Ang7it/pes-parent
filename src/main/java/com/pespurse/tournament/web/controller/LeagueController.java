package com.pespurse.tournament.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.tournament.web.dto.SaveLeagueDTO;
import com.pespurse.tournament.web.dto.TournamentInvitationDTO;
import com.pespurse.tournament.web.handler.LeagueHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tournament/league/v1/")
@AllArgsConstructor
public class LeagueController {

    private final LeagueHandler leagueHandler;

    @PostMapping(value = "createLeague")
    private Response<SaveLeagueDTO> createNewLeague(@RequestBody SaveLeagueDTO saveTournamentDTO, @RequestHeader Long userId) {
        return leagueHandler.createNewLeague(saveTournamentDTO, userId);
    }

    @PostMapping(value = "startRegistration/{leagueId}")
    private Response<SaveLeagueDTO> startRegistration(@PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.startRegistration(leagueId, userId);
    }

    @PostMapping(value = "endRegistration/{leagueId}")
    private Response<SaveLeagueDTO> endRegistration(@PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.endRegistration(leagueId, userId);
    }

    @PostMapping(value = "register/{leagueId}")
    private Response<SaveLeagueDTO> register(@PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.register(leagueId, userId);
    }

//    @PostMapping(value = "unRegister/{leagueId}")
//    private Response<SaveLeagueDTO> unRegister(@PathVariable Long leagueId, @RequestHeader Long userId) {
//        return leagueHandler.unRegister(leagueId, userId);
//    }

    @PostMapping(value = "invite/{leagueId}")
    private Response<SaveLeagueDTO> inviteParticipants(@PathVariable Long leagueId, @RequestHeader Long userId, @RequestBody List<Long> participantIds) {
        return leagueHandler.inviteParticipants(leagueId, userId, participantIds);
    }

    @PostMapping(value = "acceptInvitation")
    private Response<TournamentInvitationDTO> acceptInvitation(@RequestBody TournamentInvitationDTO tournamentInvitationDTO, @RequestHeader Long userId) {
        return leagueHandler.acceptInvitation(tournamentInvitationDTO, userId);
    }

    @PostMapping(value = "declineInvitation")
    private Response<TournamentInvitationDTO> declineInvitation(@RequestBody TournamentInvitationDTO tournamentInvitationDTO, @RequestHeader Long userId) {
        return leagueHandler.declineInvitation(tournamentInvitationDTO, userId);
    }

    @PostMapping(value = "generateLeagueFixtures/{leagueId}")
    private Response<List<SaveMatchDTO>> generateFixtures(@PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.generateLeagueFixtures(leagueId, userId);
    }

    @PostMapping(value = "saveFixture/{leagueId}")
    private Response<List<SaveMatchDTO>> saveFixtures(@PathVariable Long leagueId, @RequestHeader Long userId, @RequestBody List<SaveMatchDTO> fixtures) {
        return leagueHandler.saveFixtures(leagueId, userId, fixtures);
    }

    @GetMapping(value = "getFixture/{leagueId}")
    private Response<List<SaveMatchDTO>> getFixtures(@PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.getFixtures(leagueId, userId);
    }


//    @PostMapping(value = "createSuperLeague")
//    private Response<SaveLeagueDTO> createNewSuperLeague(@RequestBody SaveSuperLeagueDTO saveSuperLeagueDTO, @RequestHeader Long userId) {
//        return leagueHandler.createNewSuperLeague(saveSuperLeagueDTO, userId);
//    }

    @PutMapping(value = "update/{leagueId}")
    private Response<SaveLeagueDTO> updateLeague(@RequestBody SaveLeagueDTO saveTournamentDTO, @PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.updateLeague(saveTournamentDTO, leagueId, userId);
    }

    @GetMapping(value = "getById/{leagueId}")
    private Response<SaveLeagueDTO> getLeague(@PathVariable Long leagueId, @RequestHeader Long userId) {
        return leagueHandler.getLeagueById(leagueId, userId);
    }

    @PostMapping("/internal/register/{leagueId}")
    private Response<SaveLeagueDTO> addDummyParticipants(@PathVariable Long leagueId, @RequestBody List<Long> participantIds) {
        return leagueHandler.addDummyParticipants(leagueId, participantIds);
    }

    private void deleteTournament(){}
}
