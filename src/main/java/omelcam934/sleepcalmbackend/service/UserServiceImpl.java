package omelcam934.sleepcalmbackend.service;

import omelcam934.sleepcalmbackend.models.User;
import omelcam934.sleepcalmbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User u) {
        return userRepository.save(u);
    }

    @Override
    public void delete(User u) {
        userRepository.delete(u);
    }

    @Override
    public Optional<User> findByEmailLikeIgnoreCase(String email) {
        return userRepository.findByEmailLikeIgnoreCase(email);
    }

    @Override
    public Optional<User> findByUsernameLikeIgnoreCase(String username) {
        return userRepository.findByUsernameLikeIgnoreCase(username);
    }

    @Override
    public boolean existsByUsernameLikeIgnoreCase(String username) {
        return userRepository.existsByUsernameLikeIgnoreCase(username);
    }

    @Override
    public boolean existsByEmailLikeIgnoreCase(String email) {
        return userRepository.existsByEmailLikeIgnoreCase(email);
    }

    @Override
    public Optional<User> findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(String username, String email) {
        return userRepository.findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(username,email);
    }

    @Override
    public boolean existsByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(String username, String email) {
        return userRepository.existsByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(username, email);
    }
}
