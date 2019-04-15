package com.project.booking.forms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class LoginForm {
    private String login;
    private String password;
}
