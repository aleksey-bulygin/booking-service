package com.project.booking.service;


import com.project.booking.entity.Employeer;
import com.project.booking.entity.Role;
import com.project.booking.forms.EmployeerForm;
import com.project.booking.repository.EmployeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeerServiceImpl implements EmployeerService {

    private final EmployeerRepository employeerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeerServiceImpl(EmployeerRepository employeerRepository, PasswordEncoder passwordEncoder) {
        this.employeerRepository = employeerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(EmployeerForm employeerForm) {

        String hashPassword = passwordEncoder.encode(employeerForm.getPassword());

        Employeer employeer = Employeer.builder()
                .name(employeerForm.getName())
                .password(hashPassword)
                .role(Role.USER)
                .active(true)
                .build();

        employeerRepository.save(employeer);
    }

    @Override
    public List<Employeer> findAll() {
        return employeerRepository.findAll();
    }

    @Override
    public Employeer findOne(Long id) {
        return employeerRepository.getOne(id);
    }

    @Override
    public void remove(Employeer employeer) {
        employeerRepository.delete(employeer);
        employeerRepository.flush();
    }

    @Override
    public Optional<Employeer> findById(Long id) {
        return employeerRepository.findById(id);
    }

}
