package com.project.booking.service;

import com.project.booking.entity.Employeer;
import com.project.booking.forms.EmployeerForm;

import java.util.List;
import java.util.Optional;

public interface EmployeerService {
    void signUp(EmployeerForm employeerForm);

    List<Employeer> findAll();

    Employeer findOne(Long id);

    void remove(Employeer employeer);

    Optional<Employeer> findById(Long id);

}
