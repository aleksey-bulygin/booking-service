package com.project.booking.controller;

import com.project.booking.dto.TokenDto;
import com.project.booking.exception.BadRequestException;
import com.project.booking.exception.NotAcceptableException;
import com.project.booking.forms.LoginForm;
import com.project.booking.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<TokenDto> logn(@RequestBody LoginForm loginForm){
        if (loginForm == null)
            new BadRequestException("loginForm is null");

        if (loginForm.getLogin().trim().isEmpty() || loginForm.getPassword().trim().isEmpty())
            new NotAcceptableException("login or password is not correct");

        return ResponseEntity.ok().body(loginService.login(loginForm));
    }

}
