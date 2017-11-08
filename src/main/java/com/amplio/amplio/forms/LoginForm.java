package com.amplio.amplio.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginForm {
    private String userName;
    private String password;
}
