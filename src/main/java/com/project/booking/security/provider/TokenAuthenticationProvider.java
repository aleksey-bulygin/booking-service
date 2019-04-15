package com.project.booking.security.provider;

import com.project.booking.repository.TokenRepository;
import com.project.booking.entity.Token;
import com.project.booking.security.token.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenAuthenticationProvider(TokenRepository tokenRepository,
                                       @Qualifier("employeerDetailServiceImpl") UserDetailsService userDetailsService) {
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        Optional<Token> tokenCandidate = tokenRepository.findOneByValue(tokenAuthentication.getName());

        if (tokenCandidate.isPresent()) {
            Token token = tokenCandidate.get();
            UserDetails emplDetails = userDetailsService.loadUserByUsername(token.getEmployeer().getName());
            tokenAuthentication.setUserDetails(emplDetails);
            tokenAuthentication.setAuthenticated(true);
            return tokenAuthentication;
        } else
            throw new IllegalArgumentException("Bad token");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
