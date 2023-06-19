package lt.code.academy.formapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.formapi.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Andrius Baltrunas
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails
{
    private UUID id;
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
    /*@NotBlank
    @Size(min = 2, max = 100)*/
    private String surname;
    @NotBlank
    private String phone;
    @NotBlank
    private String username;
    @Email
    private String email;
    private Set<Role> roles;
    @NotBlank
    @Size(min = 10)
    private String password;
    @NotBlank
    private String repeatPassword;

    public static User convert(UserEntity entity)
    {
        Set<Role> roles = entity.getRoles()
                .stream()
                .map(Role::convert)
                .collect(Collectors.toSet());

        return new User(entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getPhone(),
                entity.getUsername(),
                entity.getEmail(),
                roles,
                entity.getPassword(),
                null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public String getFullName()
    {
        return String.format("%s %s", name, surname);
    }
}