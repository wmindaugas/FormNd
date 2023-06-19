package lt.code.academy.formapi.security.exception;

import lombok.Getter;
import lt.code.academy.formapi.user.dto.Role;
import lt.code.academy.formapi.user.dto.User;

import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class LoginUser {
    private final String fullName;
    private final String username;
    private final Set<String> roles;

    public LoginUser(User user) {
        fullName = user.getFullName();
        username = user.getUsername();
        roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}