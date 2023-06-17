package com.practice.board.domain.service.user;

import com.practice.board.domain.persistence.user.User;
import com.practice.board.domain.persistence.user.UserRepository;
import com.practice.board.domain.presentation.dto.request.*;
import com.practice.board.domain.presentation.dto.response.*;
import com.practice.board.domain.service.exception.user.PasswordMismatchException;
import com.practice.board.domain.service.exception.board.*;
import com.practice.board.domain.service.exception.user.*;
import com.practice.board.domain.service.facade.*;
import com.practice.board.global.security.jwt.JwtTokenProvider;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.websocket.OnError;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void signup(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw UserAlreadyExistException.EXCEPTION;
        }

        userRepository.save(
            User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build()
        );
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!request.getPassword().equals(user.getPassword())) {
            throw PasswordMismatchException.EXCEPTION;
        }

        return jwtTokenProvider.createToken(user.getUsername());
    }

    @Override
    public UserInfoResponse getMyInfo() {

        User currentUser = userFacade.currentUser();

        return new UserInfoResponse(currentUser);
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {

        User user = userFacade.currentUser();

        if (user.getPassword() == null) {
            throw BoardWriterMismatchException.EXCEPTION;
        }

        if (!request.getOldPassword().equals(user.getPassword())) {
            throw PasswordMismatchException.EXCEPTION;
        }

        user.changePassword(request.getNewPassword());
        userRepository.save(user);
    }

}