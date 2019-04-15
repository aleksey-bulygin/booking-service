package com.project.booking.service;

import com.project.booking.dto.TokenDto;
import com.project.booking.entity.Employeer;
import com.project.booking.entity.Token;
import com.project.booking.forms.LoginForm;
import com.project.booking.repository.EmployeerRepository;
import com.project.booking.repository.TokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeerRepository employeerRepository;

    @Autowired
    public LoginServiceImpl(TokenRepository tokenRepository, PasswordEncoder passwordEncoder, EmployeerRepository employeerRepository) {
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeerRepository = employeerRepository;
    }

    @Override
    public TokenDto login(LoginForm loginForm) {
        Optional<Employeer> employeerCandidat = employeerRepository.findOneByName(loginForm.getLogin());

        if (employeerCandidat.isPresent()) {
            Employeer employeer = employeerCandidat.get();

            if (passwordEncoder.matches(loginForm.getPassword(), employeer.getPassword())) {
                Token token = Token.builder()
                        .employeer(employeer)
                        .value(RandomStringUtils.random(13, true, true))
                        .build();

                tokenRepository.save(token);

                return TokenDto.from(token);
            }
        }
        throw new IllegalArgumentException("Employeer not found");
    }
}
