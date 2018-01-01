package services.impl;

import dto.LoginDTO;
import dto.UserDTO;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import services.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final Logger.ALogger logger = Logger.of(this.getClass());

    @Override
    public Optional<UserDTO> findUserByUsername(String username) {
        return findUserEntityByUsername(username)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<User> findUserEntityByUsername(String username) {
        return User.find.query().where().eq("username", username).findOneOrEmpty();
    }

    @Override
    public Optional<UserDTO> findUserByEmail(String email) {
        return User.find.query().where().eq("email", email).findOneOrEmpty()
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> authenticateUser(LoginDTO loginDTO) {
        return User.find.query().where().eq("username", loginDTO.username).findOneOrEmpty()
                .filter(user -> BCrypt.checkpw(loginDTO.password, user.password))
                .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        try {
            user.save();
        } catch (Exception e) {
            logger.error("Exception thrown during saveUser:", e);
            return Optional.empty();
        }
        return Optional.of(convertToDTO(user));
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.username, null, user.email, user.firstName, user.lastName);
    }

    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.username, BCrypt.hashpw(userDTO.password, BCrypt.gensalt()), userDTO.email, userDTO.firstName, userDTO.lastName, 1);
    }
}
