package com.sparta.springboardprac1.controller;

import com.sparta.springboardprac1.dto.RegisterRequestDto;
import com.sparta.springboardprac1.dto.UserInfoDto;
import com.sparta.springboardprac1.entity.UserRoleEnum;
import com.sparta.springboardprac1.security.UserDetailsImpl;
import com.sparta.springboardprac1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class AuthController {

    private final UserService userService;

    /*@GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }*/

    @PostMapping("/user/signup")
    public String signup(@Valid @RequestBody RegisterRequestDto requestDto, BindingResult bindingResult) { //@RequestBody추가. 이걸 넣지않아서 회원가입시 데이터를 보내도 입력됨을 인식하지 못함.
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "Validation error";
        }

        userService.signup(requestDto);
        return "Signup successful";
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }
}