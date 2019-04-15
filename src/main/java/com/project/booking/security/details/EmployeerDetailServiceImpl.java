package com.project.booking.security.details;

import com.project.booking.repository.EmployeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EmployeerDetailServiceImpl implements UserDetailsService {

    private final EmployeerRepository employeerRepository;

    @Autowired
    public EmployeerDetailServiceImpl(EmployeerRepository employeerRepository) {
        this.employeerRepository = employeerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return new EmployeerDetailsImpl(employeerRepository.findOneByName(name)
                .orElseThrow(IllegalArgumentException::new));
    }
}
