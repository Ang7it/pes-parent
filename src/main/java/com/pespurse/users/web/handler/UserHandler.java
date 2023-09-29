package com.pespurse.users.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;
import com.pespurse.global.exception.errorCode.UserErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.global.util.IdGenerator;
import com.pespurse.players.web.dto.UserLoginResponseDTO;
import com.pespurse.users.repo.UserEntityRepository;
import com.pespurse.users.repo.UserStatisticsEntityRepository;
import com.pespurse.users.repo.entity.UserEntity;
import com.pespurse.users.repo.entity.UserStatisticsEntity;
import com.pespurse.users.util.FakeUserUtil;
import com.pespurse.users.web.dto.UserDTO;
import com.pespurse.users.web.dto.UserStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class UserHandler {

    private final UserEntityRepository userEntityRepository;
    private final UserStatisticsEntityRepository userStatisticsEntityRepository;
    private final FakeUserUtil fakeUserUtil;

    public Response<UserDTO> handleNewUserRegistration(UserDTO userDTO) {
        log.info("In handle new user registration");
        if(Objects.nonNull(userDTO.getId())) {
            Optional<UserEntity> existingUser = userEntityRepository.findById(userDTO.getId());
            if(existingUser.isPresent()){
                log.warn("user already registered {}", userDTO);
                throw new CustomException(UserErrorCode.USER_ALREADY_REGISTERED);
            }
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity, "id");
        log.info("Registering new user {}", userDTO);
        UserEntity registeredUser = userEntityRepository.save(userEntity);
        //TODO:Emmit a Kafka event to handle all post new user registration handling
        UserStatisticsEntity userStatisticsEntity = userStatisticsEntityRepository.save(UserStatisticsEntity.builder()
                .id(IdGenerator.getInstance().getID())
                .userId(registeredUser.getId())
                .matchesPlayed(0L)
                .matchesWon(0L)
                .matchesDrawn(0L)
                .matchesLost(0L)
                .goalsScored(0L)
                .goalsAllowed(0L)
                .cleanSheets(0L)
                .goalsForwarded(0.00)
                .goalsConceded(0.00)
                .build());
        UserStatisticsDTO userStatisticsDTO = new UserStatisticsDTO();
        BeanUtils.copyProperties(userStatisticsEntity, userStatisticsDTO);
        userDTO.setUserStatisticsDTO(userStatisticsDTO);
        BeanUtils.copyProperties(registeredUser, userDTO);
        return new Response<>(userDTO);
    }

    public Response<UserLoginResponseDTO> handleUserLogin(Long userId) {
        log.info("In handle user login");
        userEntityRepository.findById(userId).orElseThrow(() -> {
            log.warn("User not registered {}", userId);
            return new CustomException(UserErrorCode.USER_NOT_REGISTERED);
        });
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        String otp = String.valueOf(new Random().nextInt(4));
        userLoginResponseDTO.setOtp(otp);
        userLoginResponseDTO.setAccessToken(UUID.randomUUID().toString());
        return new Response<>(userLoginResponseDTO);
    }

    public Response<UserDTO> getUserById(Long userId) {
        log.info("In get user by id {}", userId);
        UserEntity savedUser = userEntityRepository.findById(userId).orElseThrow(() -> {
            log.warn("User not found {}", userId);
            return new CustomException(UserErrorCode.USER_NOT_FOUND);
        });
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(savedUser, userDTO);
        return new Response<>(userDTO);
    }

    public Response<UserDTO> handleUserUpdate(Long userId, UserDTO userDTO) {
        log.info("In handle user update {}", userId);
        UserEntity savedUser = userEntityRepository.findById(userId).orElseThrow(() -> {
            log.warn("User not found {}", userId);
            return new CustomException(UserErrorCode.USER_NOT_FOUND);
        });
        BeanUtils.copyProperties(userDTO, savedUser);
        log.info("Updating user values {}", savedUser);
        BeanUtils.copyProperties(savedUser, userDTO);
        return new Response<>(userDTO);
    }

    public Response<List<UserDTO>> createDummyUsersForTest(String count) {
        if(Objects.isNull(count) || count.equals("0")){
            log.warn("Count must be greater than 0");
        }
        int createCount = Integer.parseInt(count);

        List<UserDTO> createdUsersList;
        List<UserDTO> newUsersList = new ArrayList<>(createCount);
        for(int i=0;i<createCount;i++) {
            UserDTO newUser = UserDTO.builder()
                    .userName(fakeUserUtil.getFakeUsername())
                    .userEmail(fakeUserUtil.getFakeEmailId())
                    .userPhoneNumber(fakeUserUtil.getFakePhoneNumber())
                    .userDob(fakeUserUtil.getFakeDob())
                    .userRating(fakeUserUtil.fetFakeUserRating())
                    .userType(fakeUserUtil.getFakeUserType())
                    .userCountry(fakeUserUtil.getFakeCountry())
                    .userAvatar(fakeUserUtil.getFakeAvatar())
                    .userGameId(fakeUserUtil.getFakeGameId())
                    .build();
            newUsersList.add(newUser);
        }

        createdUsersList = newUsersList.stream()
                .map(userDTO -> {
                    Response<UserDTO> userDTOResponse = handleNewUserRegistration(userDTO);
                    return userDTOResponse.getData();
                })
                .collect(Collectors.toList());

        if(createdUsersList.isEmpty()) {
            throw new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG);
        }
        return new Response<>(createdUsersList);
    }
}
