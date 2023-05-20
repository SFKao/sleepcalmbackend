package omelcam934.sleepcalmbackend.service;

import omelcam934.sleepcalmbackend.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findById(long id);
    List<User> findAll();
    User save(User u);
    void delete(User u);
    Optional<User> findByEmailLikeIgnoreCase(String email);
    Optional<User> findByUsernameLikeIgnoreCase(String username);
    boolean existsByUsernameLikeIgnoreCase(String username);
    boolean existsByEmailLikeIgnoreCase(String email);
    Optional<User> findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(String username, String email );
    boolean existsByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(String username, String email);

}
