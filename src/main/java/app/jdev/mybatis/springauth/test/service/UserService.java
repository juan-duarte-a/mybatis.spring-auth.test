package app.jdev.mybatis.springauth.test.service;

import app.jdev.mybatis.springauth.test.mapper.UserMapper;
import app.jdev.mybatis.springauth.test.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int registerNewUser(User user) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[20];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.insert(new User(null,
                user.getUsername(),
                hashedPassword,
                encodedSalt,
                user.getFirstname(),
                user.getLastname()));
    }

}
