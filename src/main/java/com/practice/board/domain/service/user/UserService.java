package com.practice.board.domain.service.user;

import com.practice.board.domain.presentation.dto.request.LoginRequest;
import com.practice.board.domain.presentation.dto.request.PasswordChangeRequest;
import com.practice.board.domain.presentation.dto.request.SignUpRequest;
import com.practice.board.domain.presentation.dto.response.TokenResponse;
import com.practice.board.domain.presentation.dto.response.UserInfoResponse;

public interface UserService {

    void signup(SignUpRequest request);

    TokenResponse login(LoginRequest request);

    UserInfoResponse getMyInfo();

    void changePassword(PasswordChangeRequest request);

}
