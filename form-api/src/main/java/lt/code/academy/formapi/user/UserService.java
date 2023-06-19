package lt.code.academy.formapi.user;

import lt.code.academy.formapi.user.dto.Role;
import lt.code.academy.formapi.user.dto.User;
import lt.code.academy.formapi.user.entity.UserEntity;
import lt.code.academy.formapi.user.exception.InvalidUserNameException;
import lt.code.academy.formapi.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        Optional<UserEntity> userByUserName = userRepository.findByUsername(user.getUsername());
        if(userByUserName.isPresent()) {
            throw new InvalidUserNameException();
        }

        user.setRoles(Set.of(new Role(UUID.fromString("b26cb831-9427-41ee-adcc-271f7b02d611"), "USER")));
        userRepository.save(UserEntity.convert(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));

        return User.convert(userEntity);
    }
}
