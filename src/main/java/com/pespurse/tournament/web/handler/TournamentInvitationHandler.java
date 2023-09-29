package com.pespurse.tournament.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.UserErrorCode;
import com.pespurse.tournament.repo.TournamentInvitationRepository;
import com.pespurse.tournament.repo.entity.TournamentInvitationEntity;
import com.pespurse.tournament.repo.entity.enums.TournamentInvitationStatus;
import com.pespurse.tournament.web.dto.TournamentInvitationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class TournamentInvitationHandler {

    private final TournamentInvitationRepository tournamentInvitationRepository;

    public void sendInvitation(TournamentInvitationDTO tournamentInvitationDTO) {
        log.info("In handle send invitation sending from {} to {}", tournamentInvitationDTO.getInvitationFrom(), tournamentInvitationDTO.getInvitationTo());
        TournamentInvitationEntity tournamentInvitationEntity = performTournamentInvitationSanityChecks(tournamentInvitationDTO);
        if(Objects.nonNull(tournamentInvitationEntity)) {
            BeanUtils.copyProperties(tournamentInvitationDTO, tournamentInvitationEntity, "id");
            tournamentInvitationRepository.save(tournamentInvitationEntity);
        }
        //TODO:Emmit a Kafka event to send the actual invitation notification
    }

    public TournamentInvitationDTO changeInvitationStatus(Long invitationId, Long userId, TournamentInvitationStatus tournamentInvitationStatus) {
        TournamentInvitationEntity tournamentInvitationEntity = tournamentInvitationRepository.findById(invitationId).orElseThrow(() -> {
            log.warn("Tournament invitation not found {}", invitationId);
            return new CustomException(UserErrorCode.USER_NOT_FOUND);
        });
        if(TournamentInvitationStatus.SENT.getTournamentInvitationStatusCode() != tournamentInvitationEntity.getInvitationStatus()
                ||  tournamentInvitationStatus.getTournamentInvitationStatusCode() == tournamentInvitationEntity.getInvitationStatus()) {
            //if invitation is already accepted or cancelled or created
            log.warn("Tournament invitation not found {}", invitationId);
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        if(!Objects.equals(tournamentInvitationEntity.getInvitationTo(), userId)) {
            log.warn("Cannot accept invitation wrong user sent id {} accepting id {}", tournamentInvitationEntity.getInvitationTo(), userId);
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        //TODO:Add the logic to add the user to the league
        tournamentInvitationEntity.setInvitationStatus(tournamentInvitationStatus.getTournamentInvitationStatusCode());
        tournamentInvitationEntity.setUpdated(LocalDateTime.now());
        tournamentInvitationRepository.save(tournamentInvitationEntity);
        TournamentInvitationDTO tournamentInvitationDTO = new TournamentInvitationDTO();
        BeanUtils.copyProperties(tournamentInvitationEntity, tournamentInvitationDTO);
        return tournamentInvitationDTO;
    }

    private TournamentInvitationEntity performTournamentInvitationSanityChecks(TournamentInvitationDTO tournamentInvitationDTO) {
        if(Objects.isNull(tournamentInvitationDTO)) {
            log.warn("Tournament invitation is null");
            //TODO:throw exception
        }
        TournamentInvitationEntity tournamentInvitationEntity = tournamentInvitationRepository.findById(tournamentInvitationDTO.getId()).orElse(new TournamentInvitationEntity());
        if(!tournamentInvitationDTO.getInvitationStatus().equals(TournamentInvitationStatus.CREATED.getTournamentInvitationStatusCode())) {
            log.warn("Tournament invitation has already been sent {}", tournamentInvitationEntity.getId());
            throw new CustomException(UserErrorCode.USER_NOT_FOUND); //TODO:Change exception
        }
        return tournamentInvitationEntity;
    }
}
