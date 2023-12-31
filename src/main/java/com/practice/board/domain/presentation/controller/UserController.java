package com.practice.board.domain.presentation.controller;


import com.practice.board.domain.presentation.dto.request.LoginRequest;
import com.practice.board.domain.presentation.dto.request.PasswordChangeRequest;
import com.practice.board.domain.presentation.dto.response.TokenResponse;
import com.practice.board.domain.presentation.dto.response.UserInfoResponse;
import com.practice.board.domain.service.user.UserService;
import com.practice.board.domain.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.practice.board.domain.presentation.dto.request.SignUpRequest;
import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignUpRequest request) {
        userService.signup(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/user")
    public UserInfoResponse getUserInfo() {
        return userService.getMyInfo();
    }

    @PatchMapping("/user/password")
    public void changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        userService.changePassword(request);
    }
}