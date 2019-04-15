package com.project.booking.controller;

import com.project.booking.forms.EmployeerForm;
import com.project.booking.service.EmployeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final EmployeerService employeerService;

    @Autowired
    public RegistrationController(EmployeerService employeerService) {
        this.employeerService = employeerService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Object> login(@RequestBody EmployeerForm employeerForm) {
        employeerService.signUp(employeerForm);

        return ResponseEntity.ok().build();
    }
}
