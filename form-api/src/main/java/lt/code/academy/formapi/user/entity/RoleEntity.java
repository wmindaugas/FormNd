package lt.code.academy.formapi.user.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;


@AllArgsConstructor
public class RoleEntity implements GrantedAuthority
{
    private static final String ROLE_PREFIX = "ROLE_";

    private UUID id;
    private String name;

    @Override
    public String getAuthority()
    {
        return ROLE_PREFIX + name;
    }

    public String getName()
    {
        return name;
    }

    public static RoleEntity convert(RoleEntity entity)
    {
        return new RoleEntity(entity.getId(), entity.getName());
    }
}