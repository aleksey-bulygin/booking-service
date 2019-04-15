package com.project.booking.service;

import com.project.booking.dto.TokenDto;
import com.project.booking.forms.LoginForm;

public interface LoginService {

    TokenDto login(LoginForm loginForm);
}
