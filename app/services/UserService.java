package services;

import dto.LoginDTO;
import dto.UserDTO;
import models.User;

import java.util.Optional;

/**
 * Service class for {@link models.User} domain objects
 *
 * @author Dusan
 */
public interface UserService {

    Optional<UserDTO> findUserByUsername(String username);

    Optional<User> findUserEntityByUsername(String username);

    Optional<UserDTO> findUserByEmail(String email);

    Optional<UserDTO> authenticateUser(LoginDTO loginDTO);

    Optional<UserDTO> saveUser(UserDTO userDTO);

}
